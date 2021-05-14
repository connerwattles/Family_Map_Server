package Test;

import DataAccess.AuthTokenDao;
import DataAccess.DataAccessException;
import DataAccess.Database;
import Model.AuthToken;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class AuthTokenDaoTest {
    private AuthTokenDao authTokenDao;
    private Database database;
    private AuthToken authTokenTest = new AuthToken();

    @BeforeEach
    public void setUp() throws DataAccessException {
        Connection conn;

        database = new Database();
        conn = database.openConnection();
        authTokenDao = new AuthTokenDao(conn);

        database.clearTables();
        database.closeConnection(true);
        conn = database.openConnection();
        authTokenDao = new AuthTokenDao(conn);
    }

    @AfterEach
    public void reset() throws DataAccessException {
        database.closeConnection(false);
        database = null;
        authTokenDao = null;
    }

    private void createAuthToken() {
        authTokenTest.setAuthToken("Default");
        authTokenTest.setUsername("connerw");
    }

    @Test
    public void testTokenFind() {
        try {
            createAuthToken();

            authTokenDao.insert(authTokenTest);

            assertEquals(authTokenTest.toString(), authTokenDao.find("connerw").toString());
        } catch (DataAccessException e) {
            fail("Data Access Exception");
        }
    }

    @Test
    public void negativeTestTokenInsert() {
        try {
            createAuthToken();

            authTokenDao.insert(authTokenTest);
        } catch (DataAccessException e) {
            fail("Data Access Exception");
        }
        AuthToken temp = new AuthToken();
        temp.setUsername("username");
        temp.setAuthToken("Default");
        assertThrows(DataAccessException.class, ()-> authTokenDao.insert(temp));
    }

    @Test
    public void negativeTestTokenFind() {
        try {
            createAuthToken();

            authTokenDao.insert(authTokenTest);

            assertEquals(null, authTokenDao.find("nonexistent"));
        } catch (DataAccessException e) {
            fail("Data Access Exception");
        }
    }

    @Test
    public void testTokenDelete() {
        try {
            createAuthToken();

            authTokenDao.insert(authTokenTest);
            database.closeConnection(true);
            Connection conn = database.openConnection();
            authTokenDao = new AuthTokenDao(conn);
            authTokenDao.delete(authTokenTest);
            assertEquals(null, authTokenDao.find("connerw"));
        } catch (DataAccessException e) {
            fail("Data Access Exception");
        }
    }
}
