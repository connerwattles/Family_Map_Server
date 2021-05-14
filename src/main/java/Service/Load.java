package Service;

import DataAccess.*;
import Model.AuthToken;
import Model.Event;
import Model.Person;
import Model.User;
import Request.LoadRequest;
import Result.LoadResult;

import java.sql.Connection;
import java.util.Random;

/**
 * This class clears data then loads the the posted
 * user, person, and event data into the database
 */
public class Load {
    private Database database = new Database();
    private UserDao userDao;
    private PersonDao personDao;
    private EventDao eventDao;
    private AuthTokenDao authTokenDao;

    public Load() throws DataAccessException {
    }

    /**
     * This begins the load service
     *
     * @param r The request values for load to operate. These are an array of users, persons, and events
     * @return The result of load is a message and successfulness
     */
    public LoadResult load(LoadRequest r) throws DataAccessException {
        Connection conn = database.openConnection();
        userDao = new UserDao(conn);
        personDao = new PersonDao(conn);
        eventDao = new EventDao(conn);
        authTokenDao = new AuthTokenDao(conn);

        User[] userList = r.getUsers();
        Person[] personList = r.getPersons();
        Event[] eventList = r.getEvents();
        LoadResult result = new LoadResult();

        if (!areUsersValid(userList) || !arePersonsValid(personList) || !areEventsValid(eventList)) {
            result.setMessage("Error: Invalid input");
            result.setSuccess(false);
            database.closeConnection(false);
            return result;
        }

        try {
            Clear clear = new Clear();
            clear.clear();
            boolean successfulInserts = true;
            successfulInserts = (insertUsers(userList) && insertPersons(personList) && insertEvents(eventList));

            if (!successfulInserts) {
                result.setMessage("Error: Inserting");
                result.setSuccess(false);
            }
            result.setMessage("Successfully added " + userList.length +
                    " users, " + personList.length + " persons, and " + eventList.length +
                    " events to the database.");
            result.setSuccess(true);
            database.closeConnection(true);
            return result;

        } catch(DataAccessException e) {
            result.setMessage("Error: Data Access Error");
            result.setSuccess(false);
            database.closeConnection(false);
            return result;
        }
    }


    public boolean insertUsers(User[] users) {
        if (users.length == 0){
            return false;
        }

        try {
            for (int i = 0; i < users.length; i++){
                User userTest = userDao.find(users[i].getUsername());
                if (userTest == null) {
                    userDao.insert(users[i]);
                    //AuthToken authToken = new AuthToken();
                    //authToken.setUsername(users[i].getUsername());
                    //authToken.setAuthToken(createID());
                    //authTokenDao.insert(authToken);
                }
                else {
                    return false;
                }
            }
        } catch (DataAccessException e) {
            return false;
        }
        return true;
    }

    public boolean insertPersons(Person[] persons) {
        if (persons.length == 0){
            return false;
        }

        try {
            for (int i = 0; i < persons.length; i++) {
                Person personTest = personDao.find(persons[i].getPersonID());
                if (personTest == null){
                    personDao.insert(persons[i]);
                }
                else {
                    return false;
                }
            }
        } catch (DataAccessException e) {
            return false;
        }
        return true;
    }

    public boolean insertEvents(Event[] events) {
        if (events.length == 0){
            return false;
        }

        try {
            for (int i = 0; i < events.length; i++){
                Event eventTest = eventDao.find(events[i].getEventID());
                if (eventTest == null){
                    eventDao.insert(events[i]);
                }
                else {
                    return false;
                }
            }
        } catch (DataAccessException e) {
            return false;
        }

        return true;
    }

    private boolean areUsersValid(User[] userArray) {
        for (int i = 0; i < userArray.length; i++){
            User currUser = userArray[i];
            if (currUser.getUsername() == null || currUser.getPassword() == null ||
                    currUser.getFirstName() == null || currUser.getLastName() == null ||
                    currUser.getEmail() == null || currUser.getGender() == null ||
                    currUser.getPersonID() == null) {

                return false;
            }
        }
        return true;
    }

    private boolean arePersonsValid(Person[] personArray) {
        for (int j = 0; j < personArray.length; j++){
            Person currPerson = personArray[j];
            if (currPerson.getPersonID() == null || currPerson.getUsername() == null ||
                    currPerson.getFirstName() == null || currPerson.getLastName() == null ||
                    currPerson.getGender() == null) {

                return false;
            }
        }
        return true;
    }

    private boolean areEventsValid(Event[] eventArray) {
        for (int k = 0; k < eventArray.length; k++){
            Event currEvent = eventArray[k];
            if (currEvent.getEventID() == null || currEvent.getUsername() == null ||
                    currEvent.getPersonID() == null || currEvent.getCity() == null ||
                    currEvent.getCountry() == null || currEvent.getEventType() == null) {

                return false;
            }
        }
        return true;
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
}
