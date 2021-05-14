package Service;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.PersonDao;
import DataAccess.UserDao;
import Model.AuthToken;
import Model.Person;
import Model.User;
import Request.FillRequest;
import Request.LoginRequest;
import Request.RegisterRequest;
import Result.LoginResult;
import Result.RegisterResult;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.util.Random;

/**
 * A class to register a new user, create its ancestors,
 * log the user in, and create the user an auth token.
 */
public class Register {
    private Database database = new Database();
    private UserDao userDao;
    private PersonDao personDao;

    public Register() throws DataAccessException {
    }

    /**
     * Begins the registration process of a new user
     *
     * @param r The request values attached to a new user
     * @return The result of creating a new user
     */
    public RegisterResult register(RegisterRequest r) throws DataAccessException, FileNotFoundException {
        Connection conn = database.openConnection();
        userDao = new UserDao(conn);
        personDao = new PersonDao(conn);

        User newUser = new User();
        newUser.setUsername(r.getUsername());
        newUser.setPassword(r.getPassword());
        newUser.setEmail(r.getEmail());
        newUser.setFirstName(r.getFirstName());
        newUser.setLastName(r.getLastName());
        newUser.setGender(r.getGender());

        Person userPerson = new Person();
        userPerson.setPersonID(createID());
        userPerson.setUsername(r.getUsername());
        userPerson.setFirstName(r.getFirstName());
        userPerson.setLastName(r.getLastName());
        userPerson.setGender(r.getGender());

        newUser.setPersonID(userPerson.getPersonID());

        if(userDao.find(newUser.getUsername()) == null) {
            userDao.insert(newUser);
            personDao.insert(userPerson);
            database.closeConnection(true);

            //Generate 4 generations
            FillRequest fillRequest = new FillRequest();
            fillRequest.setUsername(newUser.getUsername());
            fillRequest.setGenerations(4);
            Fill fillGeneration = new Fill();
            fillGeneration.fill(fillRequest);

            //Call login
            LoginRequest loginRequest = new LoginRequest();
            loginRequest.setUsername(newUser.getUsername());
            loginRequest.setPassword(newUser.getPassword());
            Login loginUser = new Login();
            LoginResult loginResult = loginUser.login(loginRequest);
            String authToken = loginResult.getAuthToken();

            RegisterResult result = new RegisterResult();
            result.setAuthToken(authToken);
            result.setUsername(newUser.getUsername());
            result.setPersonID(newUser.getPersonID());
            result.setSuccess(true);
            return result;

        }
        else {
            RegisterResult result = new RegisterResult();
            result.setMessage("Error: Username already exists!");
            result.setSuccess(false);
            database.closeConnection(false);
            return result;
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
}
