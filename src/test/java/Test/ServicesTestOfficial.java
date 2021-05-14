package Test;

import DataAccess.*;
import Model.Event;
import Model.Person;
import Model.User;
import Service.*;
import Service.JsonObjects.LoadObjects;
import Request.*;
import Result.*;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

public class ServicesTestOfficial {
    private Fill fill = new Fill();
    private Clear clear = new Clear();
    private Login login = new Login();
    private Register register = new Register();
    private Load load = new Load();
    private UserDao userDao;
    private PersonDao personDao;
    private EventDao eventDao;
    private Database database;

    public ServicesTestOfficial() throws DataAccessException, FileNotFoundException {
    }

    @BeforeEach
    public void reset() {
        clear.clear();
    }

    private void createUser(User userTest) {
        userTest.setUsername("connerw");
        userTest.setPassword("byu123");
        userTest.setEmail("conner@gmail.com");
        userTest.setFirstName("conner");
        userTest.setLastName("wattles");
        userTest.setGender("m");
        userTest.setPersonID("randID");
    }

    private void insertPersonUser() throws DataAccessException {
        try {
            Connection conn;

            database = new Database();
            conn = database.openConnection();
            userDao = new UserDao(conn);
            personDao = new PersonDao(conn);
            User userTest = new User();
            Person personTest = new Person();

            createUser(userTest);
            personTest.setPersonID("randID");
            personTest.setUsername(userTest.getUsername());
            personTest.setFirstName(userTest.getFirstName());
            personTest.setLastName(userTest.getLastName());
            personTest.setGender(userTest.getGender());

            userDao.insert(userTest);
            personDao.insert(personTest);
        } catch (DataAccessException e) {
            fail("Data Access Exception");
        }
        database.closeConnection(true);
    }

    public void insertEvent() throws DataAccessException {
        try {
            Connection conn;

            database = new Database();
            conn = database.openConnection();
            eventDao = new EventDao(conn);
            Event eventTest = new Event();

            eventTest.setEventID("09271jfn");
            eventTest.setUsername("connerw");
            eventTest.setPersonID("randID");
            eventTest.setLatitude((float) 82.5);
            eventTest.setLongitude((float) -93.0);
            eventTest.setCountry("United States");
            eventTest.setCity("Provo");
            eventTest.setEventType("birth");
            eventTest.setYear(2000);

            eventDao.insert(eventTest);
        } catch (DataAccessException e) {
            fail("Data Access Exception");
        }
        database.closeConnection(true);
    }

    @Test
    public void clearPositive() {
        try {
            insertPersonUser();
            insertEvent();

            ClearResult result = clear.clear();
            if (result.getSuccess() == false)
                fail("Clear Failed");
        } catch (DataAccessException e) {
            fail("Data Access Exception");
        }
    }

    @Test
    public void loadPositive() {
        try {
            Gson gson = new Gson();
            FileReader loadFile = new FileReader("passoffFiles/LoadData.json");
            LoadObjects loadObjects = gson.fromJson(loadFile, LoadObjects.class);
            User[] users = loadObjects.getUsers();
            Person[] persons = loadObjects.getPersons();
            Event[] events = loadObjects.getEvents();

            int userSize = users.length;
            int personsSize = persons.length;
            int eventsSize = events.length;

            LoadRequest request = new LoadRequest();
            request.setUsers(users);
            request.setPersons(persons);
            request.setEvents(events);

            LoadResult result = load.load(request);

            System.out.println(result.getMessage());

            if (result.getSuccess() == false) {
                fail("Unsuccessful Load");
            }
            else {
                if (!result.getMessage().equals("Successfully added " + userSize + " users, " + personsSize +
                        " persons, and " + eventsSize + " events to the database.")) {
                    fail("Incorrect Number of Objects Inserted");
                }
            }
        } catch (FileNotFoundException | DataAccessException e) {
            fail("File Not Found");
        }
    }

    @Test
    public void loadNegative() {
        try {
            Gson gson = new Gson();
            FileReader loadFile = new FileReader("json/LoadData.json");
            LoadObjects loadObjects = gson.fromJson(loadFile, LoadObjects.class);
            User[] users = loadObjects.getUsers();
            Person[] persons = loadObjects.getPersons();
            Event[] events = loadObjects.getEvents();

            users[0].setPassword(null);

            int userSize = users.length;
            int personsSize = persons.length;
            int eventsSize = events.length;

            LoadRequest request = new LoadRequest();
            request.setUsers(users);
            request.setPersons(persons);
            request.setEvents(events);

            LoadResult result = load.load(request);

            users[0].setPassword("parker");

            if (result.getSuccess() == true) {
                fail("Successful Load with Invalid Input");
            }
            else {
                if (!result.getMessage().equals("Error: Invalid input")) {
                    fail("Wrong Error for Invalid Input");
                }
            }
        } catch (FileNotFoundException | DataAccessException e) {
            fail("File Not Found");
        }
    }

    @Test
    public void registerPositive() {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("connerw");
        request.setPassword("byu123");
        request.setEmail("connerwattles@gmail.com");
        request.setFirstName("Conner");
        request.setLastName("Wattles");
        request.setGender("m");

        try{
            RegisterResult result = register.register(request);

            System.out.println(result.getUsername());
            System.out.println(result.getAuthToken());
            System.out.println(result.getPersonID());

            if (result.getSuccess() == false) {
                fail("Register was Unsuccessful");
            }
            else {
                if (result.getAuthToken() == null || result.getUsername() == null || result.getPersonID() == null)
                    fail("Empty Return Values");
            }
        } catch(DataAccessException | FileNotFoundException e) {
            fail("Exception Thrown");
        }
    }

    @Test
    public void registerNegative() {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("connerw");
        request.setPassword("byu123");
        request.setEmail("connerwattles@gmail.com");
        request.setFirstName("Conner");
        request.setLastName("Wattles");
        request.setGender("m");

        try{
            insertPersonUser();

            RegisterResult result = register.register(request);

            System.out.println(result.getUsername());
            System.out.println(result.getAuthToken());
            System.out.println(result.getPersonID());

            if (result.getSuccess() == false) {
                if (!result.getMessage().equals("Error: Username already exists!")) {
                    fail("Register did not notice duplicate name");
                }
            }
            else {
                fail("Register Succeeded when Username Already Exists");
            }
        } catch(DataAccessException | FileNotFoundException e) {
            fail("Exception Thrown");
        }
    }

    @Test
    public void loginPositive() {
        try {
            insertPersonUser();

            LoginRequest request = new LoginRequest();
            request.setUsername("connerw");
            request.setPassword("byu123");

            LoginResult result = login.login(request);

            System.out.println(result.getAuthToken());
            System.out.println(result.getUsername());
            System.out.println(result.getPersonID());
            if (result.getSuccess() == true) {
                if (result.getAuthToken() == null || result.getUsername() == null || result.getPersonID() == null)
                    fail("Incorrect Result from Login");
            }
            else
                fail("Unsuccessful Login");
        } catch (DataAccessException e) {
            fail("Data Access Exception");
        }
    }

    @Test
    public void loginNegative() {
        try {
            insertPersonUser();

            LoginRequest request = new LoginRequest();
            request.setUsername("connerw");
            request.setPassword("notcorrect");

            LoginResult result = login.login(request);

            System.out.println(result.getMessage());
            System.out.println(result.getSuccess());
            if (result.getSuccess() == true) {
                fail("Login Accepted Incorrect Password");
            }
            else {
                if (!result.getMessage().equals("Error: Incorrect Password"))
                    fail("Login Accepted Incorrect Password");
            }
        } catch (DataAccessException e) {
            fail("Data Access Exception");
        }
    }

    @Test
    public void fillPositiveWithGenerationTest() {
        //Must check for correct people and events generated
        try {
            insertPersonUser();

            FillRequest request = new FillRequest();
            request.setUsername("connerw");
            request.setGenerations(4);

            FillResult result = new FillResult();
            result = fill.fill(request);

            System.out.println(result.getMessage());
            System.out.println(result.getSuccess());
            if (!result.getMessage().equals("Successfully added 31 persons and 91 events to the database."))
                fail("Incorrect number of persons and events inserted");
        } catch (FileNotFoundException e) {
            fail("File Not Found");
        } catch (DataAccessException e) {
            fail("Data Access Exception");
        }
    }

    @Test
    public void fillNegative() {
        try {
            insertPersonUser();

            FillRequest request = new FillRequest();
            request.setUsername("connerw");
            request.setGenerations(-1);

            FillResult result = new FillResult();
            result = fill.fill(request);

            System.out.println(result.getMessage());
            System.out.println(result.getSuccess());
            if (result.getSuccess() != false)
                fail("Fill Succeeded With Invalid Generations");
        } catch (FileNotFoundException e) {
            fail("File Not Found");
        } catch (DataAccessException e) {
            fail("Data Access Exception");
        }
    }

    @Test
    public void personPositive() {
        RegisterRequest regRequest = new RegisterRequest();
        regRequest.setUsername("connerw");
        regRequest.setPassword("byu123");
        regRequest.setEmail("connerwattles@gmail.com");
        regRequest.setFirstName("Conner");
        regRequest.setLastName("Wattles");
        regRequest.setGender("m");

        RegisterResult regResult = new RegisterResult();

        try{
            regResult = register.register(regRequest);

            PersonsRequest personRequest = new PersonsRequest();
            personRequest.setAuthToken(regResult.getAuthToken());
            personRequest.setPersonID("randID");

            try {
                Connection conn;

                database = new Database();
                conn = database.openConnection();
                personDao = new PersonDao(conn);

                Person testPerson = new Person();
                testPerson.setPersonID("randID");
                testPerson.setUsername("connerw");
                testPerson.setFirstName("Conner");
                testPerson.setLastName("Wattles");
                testPerson.setGender("m");

                personDao.insert(testPerson);
            } catch (DataAccessException e) {
                fail("Data Access Exception");
            }
            database.closeConnection(true);

            Persons personsService = new Persons();
            PersonsResult result = personsService.persons(personRequest);

            System.out.println(result.getUsername());
            System.out.println(result.getPersonID());
            System.out.println(result.getFirstName());
            System.out.println(result.getLastName());
            System.out.println(result.getGender());
            System.out.println(result.getFatherID());
            System.out.println(result.getMotherID());
            System.out.println(result.getSpouseID());
            System.out.println(result.getSuccess());

            if (result.getSuccess() == false) {
                fail("Person was not Successful");
            }
            else {
                if (result.getUsername() == null || result.getPersonID() == null || result.getFirstName() == null
                        || result.getLastName() == null || result.getGender() == null) {
                    fail("Invalid Return variables");
                }
            }
        } catch(DataAccessException | FileNotFoundException e) {
            fail("Exception Thrown");
        }
    }

    @Test
    public void personNegative() {
        RegisterRequest regRequest = new RegisterRequest();
        regRequest.setUsername("connerw");
        regRequest.setPassword("byu123");
        regRequest.setEmail("connerwattles@gmail.com");
        regRequest.setFirstName("Conner");
        regRequest.setLastName("Wattles");
        regRequest.setGender("m");

        RegisterResult regResult = new RegisterResult();

        try{
            regResult = register.register(regRequest);

            PersonsRequest personRequest = new PersonsRequest();
            personRequest.setAuthToken(regResult.getAuthToken());
            personRequest.setPersonID("nonExistantID");

            try {
                Connection conn;

                database = new Database();
                conn = database.openConnection();
                personDao = new PersonDao(conn);

                Person testPerson = new Person();
                testPerson.setPersonID("randID");
                testPerson.setUsername("connerw");
                testPerson.setFirstName("Conner");
                testPerson.setLastName("Wattles");
                testPerson.setGender("m");

                personDao.insert(testPerson);
            } catch (DataAccessException e) {
                fail("Data Access Exception");
            }
            database.closeConnection(true);

            Persons personsService = new Persons();
            PersonsResult result = personsService.persons(personRequest);

            System.out.println(result.getUsername());
            System.out.println(result.getPersonID());
            System.out.println(result.getFirstName());
            System.out.println(result.getLastName());
            System.out.println(result.getGender());
            System.out.println(result.getFatherID());
            System.out.println(result.getMotherID());
            System.out.println(result.getSpouseID());
            System.out.println(result.getSuccess());

            if (result.getSuccess() == true) {
                fail("Person Succeeded");
            }
            else {
                if (!result.getMessage().equals("Error: Person not found")) {
                    fail("Incorrect Error");
                }
            }

        } catch(DataAccessException | FileNotFoundException e) {
            fail("Exception Thrown");
        }
    }

    @Test
    public void personsPositive() {
        try {
            RegisterRequest regRequest = new RegisterRequest();
            regRequest.setUsername("connerw");
            regRequest.setPassword("byu123");
            regRequest.setEmail("connerwattles@gmail.com");
            regRequest.setFirstName("Conner");
            regRequest.setLastName("Wattles");
            regRequest.setGender("m");

            RegisterResult regResult = register.register(regRequest);

            PersonsRequest request = new PersonsRequest();
            request.setAuthToken(regResult.getAuthToken());
            Persons personsService = new Persons();
            PersonsResult result = personsService.persons(request);

            Person[] persons = result.getData();
            if (persons.length != 31) {
                fail("Incorrect number of Persons Returned");
            }
            for (int i = 0; i < persons.length; i++) {
                if (persons[i] == null) {
                    fail("Invalid User Inputted in Array");
                }
            }
        } catch (DataAccessException | FileNotFoundException e) {
            fail("Data Access Exception");
        }
    }

    @Test
    public void personsNegative() {
        try {
            RegisterRequest regRequest = new RegisterRequest();
            regRequest.setUsername("connerw");
            regRequest.setPassword("byu123");
            regRequest.setEmail("connerwattles@gmail.com");
            regRequest.setFirstName("Conner");
            regRequest.setLastName("Wattles");
            regRequest.setGender("m");

            RegisterResult regResult = register.register(regRequest);

            database = new Database();
            database.openConnection();
            database.clearPersons();
            database.closeConnection(true);

            PersonsRequest request = new PersonsRequest();
            request.setAuthToken(regResult.getAuthToken());
            Persons personsService = new Persons();
            PersonsResult result = personsService.persons(request);

            Person[] persons = result.getData();

            if (!result.getMessage().equals("Error: No Persons")) {
                fail("Incorrect number of Persons Returned");
            }

        } catch (DataAccessException | FileNotFoundException e) {
            fail("Data Access Exception");
        }
    }

    @Test
    public void eventPositive() {
        RegisterRequest regRequest = new RegisterRequest();
        regRequest.setUsername("connerw");
        regRequest.setPassword("byu123");
        regRequest.setEmail("connerwattles@gmail.com");
        regRequest.setFirstName("Conner");
        regRequest.setLastName("Wattles");
        regRequest.setGender("m");

        RegisterResult regResult = new RegisterResult();

        try {
            regResult = register.register(regRequest);

            EventsRequest eventRequest = new EventsRequest();
            eventRequest.setAuthToken(regResult.getAuthToken());
            eventRequest.setEventID("randID");

            try {
                Connection conn;

                database = new Database();
                conn = database.openConnection();
                eventDao = new EventDao(conn);

                Event testEvent = new Event();
                testEvent.setUsername("connerw");
                testEvent.setEventID("randID");
                testEvent.setPersonID("10DFHS3f");
                testEvent.setLatitude((float)23.1);
                testEvent.setLongitude((float)45.2);
                testEvent.setCountry("Japan");
                testEvent.setCity("Tokyo");
                testEvent.setEventType("birth");
                testEvent.setYear(2000);

                eventDao.insert(testEvent);
            } catch (DataAccessException e) {
                fail("Data Access Exception");
            }
            database.closeConnection(true);

            Events eventsService = new Events();
            EventsResult result = eventsService.event(eventRequest);

            System.out.println(result.getUsername());
            System.out.println(result.getEventID());
            System.out.println(result.getPersonID());
            System.out.println(result.getLatitude());
            System.out.println(result.getLongitude());
            System.out.println(result.getCountry());
            System.out.println(result.getCity());
            System.out.println(result.getEventType());
            System.out.println(result.getSuccess());

            if (result.getSuccess() == false) {
                fail("Event was not Successful");
            }
            else {
                if (result.getUsername() == null || result.getEventID() == null || result.getPersonID() == null
                        || result.getCountry() == null || result.getCity() == null || result.getEventType() == null) {
                    fail("Invalid Return variables");
                }
            }
        } catch (FileNotFoundException | DataAccessException e) {
            fail("Exception Error");
        }
    }

    @Test
    public void eventNegative() {

        RegisterRequest regRequest = new RegisterRequest();
        regRequest.setUsername("connerw");
        regRequest.setPassword("byu123");
        regRequest.setEmail("connerwattles@gmail.com");
        regRequest.setFirstName("Conner");
        regRequest.setLastName("Wattles");
        regRequest.setGender("m");

        RegisterResult regResult = new RegisterResult();

        try {
            regResult = register.register(regRequest);

            EventsRequest eventRequest = new EventsRequest();
            eventRequest.setAuthToken(regResult.getAuthToken());
            eventRequest.setEventID("nonExistantID");

            try {
                Connection conn;

                database = new Database();
                conn = database.openConnection();
                eventDao = new EventDao(conn);

                Event testEvent = new Event();
                testEvent.setUsername("connerw");
                testEvent.setEventID("randID");
                testEvent.setPersonID("10DFHS3f");
                testEvent.setLatitude((float)23.1);
                testEvent.setLongitude((float)45.2);
                testEvent.setCountry("Japan");
                testEvent.setCity("Tokyo");
                testEvent.setEventType("birth");
                testEvent.setYear(2000);

                eventDao.insert(testEvent);
            } catch (DataAccessException e) {
                fail("Data Access Exception");
            }
            database.closeConnection(true);

            Events eventsService = new Events();
            EventsResult result = eventsService.event(eventRequest);

            if (result.getSuccess() == true) {
                fail("Event was Successful when Event did not exist");
            }
            else {
                if (!result.getMessage().equals("Error: Event not found")) {
                    fail("Wrong Error Encountered");
                }
            }
        } catch (FileNotFoundException | DataAccessException e) {
            fail("Exception Error");
        }
    }

    @Test
    public void eventsPositive() {
        try {
            RegisterRequest regRequest = new RegisterRequest();
            regRequest.setUsername("connerw");
            regRequest.setPassword("byu123");
            regRequest.setEmail("connerwattles@gmail.com");
            regRequest.setFirstName("Conner");
            regRequest.setLastName("Wattles");
            regRequest.setGender("m");

            RegisterResult regResult = register.register(regRequest);

            EventsRequest request = new EventsRequest();
            request.setAuthToken(regResult.getAuthToken());
            Events eventsService = new Events();
            EventsResult result = eventsService.event(request);

            Event[] events = result.getData();
            if (events.length != 91) {
                fail("Incorrect number of Persons Returned");
            }
            for (int i = 0; i < events.length; i++) {
                if (events[i] == null) {
                    fail("Invalid Event Inputted in Array");
                }
            }
        } catch (DataAccessException | FileNotFoundException e) {
            fail("Data Access Exception");
        }
    }

    @Test
    public void eventsNegative() {
        try {
            RegisterRequest regRequest = new RegisterRequest();
            regRequest.setUsername("connerw");
            regRequest.setPassword("byu123");
            regRequest.setEmail("connerwattles@gmail.com");
            regRequest.setFirstName("Conner");
            regRequest.setLastName("Wattles");
            regRequest.setGender("m");

            RegisterResult regResult = register.register(regRequest);

            database = new Database();
            database.openConnection();
            database.clearEvents();
            database.closeConnection(true);

            EventsRequest request = new EventsRequest();
            request.setAuthToken(regResult.getAuthToken());
            Events eventsService = new Events();
            EventsResult result = eventsService.event(request);

            Event[] events = result.getData();

            if (!result.getMessage().equals("Error: No Events")) {
                fail("Incorrect number of Events Returned");
            }
        } catch (DataAccessException | FileNotFoundException e) {
            fail("Data Access Exception");
        }
    }
}
