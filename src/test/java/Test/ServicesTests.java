package Test;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.PersonDao;
import DataAccess.UserDao;
import Model.Person;
import Model.User;
import Service.Clear;
import Service.Fill;
import Service.Login;
import Service.Register;
import Request.FillRequest;
import Request.LoginRequest;
import Request.RegisterRequest;
import Result.FillResult;
import Result.LoginResult;
import Result.RegisterResult;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

public class ServicesTests {
    private Fill fill = new Fill();
    private Clear clear = new Clear();
    private Login login = new Login();
    private Register register = new Register();
    private UserDao userDao;
    private PersonDao personDao;
    private Database database;

    public ServicesTests() throws FileNotFoundException, DataAccessException {
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
        Connection conn;

        database = new Database();
        conn = database.openConnection();
        userDao = new UserDao(conn);
        personDao = new PersonDao(conn);
        User userTest = new User();
        Person personTest = new Person();

        try {
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

    @Test
    public void testFill() throws DataAccessException, FileNotFoundException {
        insertPersonUser();

        FillRequest request = new FillRequest();
        request.setUsername("connerw");
        request.setGenerations(4);

        FillResult result = new FillResult();
        result = fill.fill(request);

        System.out.println(result.getMessage());
        System.out.println(result.getSuccess());

        //clear.clear();
    }

    @Test
    public void clear() throws DataAccessException {
        //insertPersonUser();

        clear.clear();
    }

    @Test
    public void login() throws DataAccessException {
        insertPersonUser();

        LoginRequest request = new LoginRequest();
        request.setUsername("connerw");
        request.setPassword("byu123");

        LoginResult result = login.login(request);

        System.out.println(result.getAuthToken());
        System.out.println(result.getUsername());
        System.out.println(result.getPersonID());
    }

    @Test
    public void register() {
        //Fix Login not finding common user with username

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
        } catch(DataAccessException | FileNotFoundException e) {
            return;
        }
    }

    @Test
    public void dummy() {
        try {
            insertPersonUser();
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }
}
