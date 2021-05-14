package Service;

import DataAccess.*;
import Model.Event;
import Model.Person;
import Model.User;
import Service.JsonObjects.Location;
import Service.JsonObjects.Locations;
import Service.JsonObjects.Names;
import Request.FillRequest;
import Result.FillResult;
import Result.RegisterResult;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.util.Random;

/**
 * A class to populate a database with generated data
 * for a specified user
 */
public class Fill {
    private Database database = new Database();
    private UserDao userDao;
    private PersonDao personDao;
    private EventDao eventDao;
    private ClearBasedUsername clear;
    private Gson gson = new Gson();
    private FileReader locationsFile = new FileReader("json/locations.json");
    private Locations locations = gson.fromJson(locationsFile, Locations.class);
    private FileReader fNamesFile = new FileReader("json/fnames.json");
    private Names femaleNames = gson.fromJson(fNamesFile, Names.class);
    private FileReader mNamesFile = new FileReader("json/mnames.json");
    private Names maleNames = gson.fromJson(mNamesFile, Names.class);
    private FileReader sNamesFile = new FileReader("json/snames.json");
    private Names surNames = gson.fromJson(sNamesFile, Names.class);
    private int personsCount = 0;
    private int eventsCount = 0;
    private final int currentYear = 2021;

    public Fill() throws DataAccessException, FileNotFoundException {
    }

    /**
     * Begins the process of generating data for a user based on their username
     * and creates generations of ancestors based on a specified number
     *
     * @param f The request values are a username and number of generations
     * @return The resulting message and success of the service
     */
    public FillResult fill(FillRequest f) throws DataAccessException, FileNotFoundException {
        Connection conn = database.openConnection();
        userDao = new UserDao(conn);
        personDao = new PersonDao(conn);
        eventDao = new EventDao(conn);
        clear  = new ClearBasedUsername(conn);

        String username = f.getUsername();
        int numGen = f.getGenerations();
        User currUser = new User();
        currUser = userDao.find(username);
        if(currUser == null) {
            FillResult result = new FillResult();
            result.setMessage("Error: User does not exist");
            result.setSuccess(false);
            database.closeConnection(false);
            return result;
        }
        Person currPerson = new Person();
        currPerson = personDao.find(currUser.getPersonID());

        clear.delete(username);


        Event personEvent = new Event();
        personEvent.setEventID(createID());
        personEvent.setUsername(currPerson.getUsername());
        personEvent.setPersonID(currPerson.getPersonID());
        personEvent.setYear(getRandomNumber(1990, 2020));
        personEvent.setEventType("birth");
        Location birthLocation = locations.getAllLocations()[getRandomNumber(0, locations.getAllLocations().length)];
        personEvent.setLatitude(birthLocation.getLatitude());
        personEvent.setLongitude(birthLocation.getLongitude());
        personEvent.setCountry(birthLocation.getCountry());
        personEvent.setCity(birthLocation.getCity());
        eventDao.insert(personEvent);
        eventsCount++;

        if (numGen <= 0) {
            FillResult result = new FillResult();
            result.setMessage("Error: Invalid Number of Generations");
            result.setSuccess(false);
            database.closeConnection(false);
            return result;
        }

        createGenerations(currPerson, 0, numGen, personEvent.getYear());

        database.closeConnection(true);

        FillResult result = new FillResult();
        result.setMessage("Successfully added " + personsCount + " persons and " + eventsCount +
                " events to the database.");
        result.setSuccess(true);
        return result;
    }

    public void createGenerations(Person p, int genCount, int gen, int baseYear) throws DataAccessException, FileNotFoundException {
        if (genCount == gen) {
            personDao.insert(p);
            personsCount++;
            return;
        }
        else {
            Person father = createFather(p);
            Person mother = createMother(p);

            //Set spouseID
            father.setSpouseID(mother.getPersonID());
            mother.setSpouseID(father.getPersonID());

            //Create events for father and mother

            Event fatherBirth = createBirth(p, father, baseYear);
            Event motherBirth = createBirth(p, mother, baseYear);


            int highestBirthYear;
            if (fatherBirth.getYear() <= motherBirth.getYear())
                highestBirthYear = motherBirth.getYear();
            else
                highestBirthYear = fatherBirth.getYear();
            int marriageYear = getRandomNumber((highestBirthYear+18), (baseYear-1));
            Location marriageLocation = locations.getAllLocations()[getRandomNumber(0, locations.getAllLocations().length)];
            Event fatherMarriage = createMarriage(p, father, marriageYear, marriageLocation);
            Event motherMarriage = createMarriage(p, mother, marriageYear, marriageLocation);

            int fatherDeathYear = getRandomNumber((baseYear+1), (fatherBirth.getYear()+100));
            if (fatherDeathYear > currentYear)
                fatherDeathYear = currentYear;
            Event fatherDeath = createDeath(p, father, fatherDeathYear);
            int motherDeathYear = getRandomNumber((baseYear+1), (motherBirth.getYear()+100));
            if (motherDeathYear > currentYear)
                motherDeathYear = currentYear;
            Event motherDeath = createDeath(p, mother, motherDeathYear);

            p.setFatherID(father.getPersonID());
            p.setMotherID(mother.getPersonID());

            personDao.insert(p);
            personsCount++;

            createGenerations(father, genCount+1, gen, fatherBirth.getYear());
            createGenerations(mother, genCount+1, gen, motherBirth.getYear());
        }
    }

    private String createID() {
        String RANDCHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder rand = new StringBuilder();
        Random rnd = new Random();
        while (rand.length() < 8) { // length of the random string.
            int index = (int) (rnd.nextFloat() * RANDCHARS.length());
            rand.append(RANDCHARS.charAt(index));
        }
        String saltStr = rand.toString();
        return saltStr;

    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public Person createFather(Person p) {
        Person father = new Person();
        father.setPersonID(createID());
        father.setUsername(p.getUsername());
        father.setFirstName(maleNames.getNames()[getRandomNumber(0, maleNames.getNames().length)]);
        father.setLastName(p.getLastName());
        father.setGender("m");
        return father;
    }

    public Person createMother(Person p) {
        Person mother = new Person();
        mother.setPersonID(createID());
        mother.setUsername(p.getUsername());
        mother.setFirstName(femaleNames.getNames()[getRandomNumber(0, femaleNames.getNames().length)]);
        mother.setLastName(surNames.getNames()[getRandomNumber(0, surNames.getNames().length)]);
        mother.setGender("f");
        return mother;
    }

    public Event createBirth(Person p, Person p1, int year) throws FileNotFoundException, DataAccessException {
        Event birth = new Event();
        birth.setEventID(createID());
        birth.setUsername(p.getUsername());
        birth.setPersonID(p1.getPersonID());
        birth.setYear(getRandomNumber((year-30), (year-18)));
        birth.setEventType("birth");
        Location fBirthLocation = locations.getAllLocations()[getRandomNumber(0, locations.getAllLocations().length)];
        birth.setLatitude(fBirthLocation.getLatitude());
        birth.setLongitude(fBirthLocation.getLongitude());
        birth.setCountry(fBirthLocation.getCountry());
        birth.setCity(fBirthLocation.getCity());
        eventDao.insert(birth);
        eventsCount++;
        return birth;
    }

    public Event createMarriage(Person p, Person p1, int marriageYear, Location marriageLocation) throws DataAccessException {
        Event marriage = new Event();
        marriage.setEventID(createID());
        marriage.setUsername(p.getUsername());
        marriage.setPersonID(p1.getPersonID());
        marriage.setYear(marriageYear);
        marriage.setEventType("marriage");
        marriage.setLatitude(marriageLocation.getLatitude());
        marriage.setLongitude(marriageLocation.getLongitude());
        marriage.setCountry(marriageLocation.getCountry());
        marriage.setCity(marriageLocation.getCity());
        eventDao.insert(marriage);
        eventsCount++;
        return marriage;
    }

    public Event createDeath(Person p, Person p1, int year) throws FileNotFoundException, DataAccessException {
        Event death = new Event();
        death.setEventID(createID());
        death.setUsername(p.getUsername());
        death.setPersonID(p1.getPersonID());
        death.setYear(year);
        death.setEventType("death");
        Location fDeathLocation = locations.getAllLocations()[getRandomNumber(0, locations.getAllLocations().length)];
        death.setLatitude(fDeathLocation.getLatitude());
        death.setLongitude(fDeathLocation.getLongitude());
        death.setCountry(fDeathLocation.getCountry());
        death.setCity(fDeathLocation.getCity());
        eventDao.insert(death);
        eventsCount++;
        return death;
    }
}
