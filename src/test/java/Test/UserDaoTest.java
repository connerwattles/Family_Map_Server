package Test;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.UserDao;
import Model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;

public class UserDaoTest {
    private UserDao userDao;
    private Database database;
    private User userTest = new User();

    @BeforeEach
    public void setUp() throws DataAccessException {
        Connection conn;

        database = new Database();
        conn = database.openConnection();
        userDao = new UserDao(conn);

        database.clearTables();
        database.closeConnection(true);
        conn = database.openConnection();
        userDao = new UserDao(conn);
    }

    @AfterEach
    public void reset() throws DataAccessException {
        database.closeConnection(false);
        database = null;
        userDao = null;
    }

    private void createUser() {
        userTest.setUsername("connerw");
        userTest.setPassword("byu123");
        userTest.setEmail("conner@gmail.com");
        userTest.setFirstName("conner");
        userTest.setLastName("wattles");
        userTest.setGender("m");
        userTest.setPersonID("randID");
    }

    @Test
    public void testUserInsertFind() {
        try {
            createUser();

            userDao.insert(userTest);

            assertEquals(userTest.toString(), userDao.find("connerw").toString());
        } catch (DataAccessException e) {
            fail("Data Access Exception");
        }
    }

    @Test
    public void negativeTestUserInsert() {
        try {
            createUser();

            userDao.insert(userTest);
        } catch (DataAccessException e) {
            fail("Data Access Exception");
        }
        User temp = new User();
        temp.setUsername("connerw");
        temp.setPassword("temp");
        temp.setEmail("temp");
        temp.setFirstName("temp");
        temp.setLastName("temp");
        temp.setGender("m");
        temp.setPersonID("temp");
        assertThrows(DataAccessException.class, ()-> userDao.insert(temp));
    }

    @Test
    public void negativeTestUserFind() {
        try {
            createUser();

            userDao.insert(userTest);

            assertEquals(null, userDao.find("nonexistent"));
        } catch (DataAccessException e) {
            fail("Data Access Exception");
        }
    }

    @Test
    public void testUserDelete() {
      try {
          createUser();

          userDao.insert(userTest);
          database.closeConnection(true);
          Connection conn = database.openConnection();
          userDao = new UserDao(conn);
          userDao.delete(userTest);
          assertEquals(null, userDao.find("connerw"));
        } catch (DataAccessException e) {
          fail("Data Access Exception");
      }
    }
}
