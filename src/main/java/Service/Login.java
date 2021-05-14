package Service;

import DataAccess.AuthTokenDao;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.UserDao;
import Model.AuthToken;
import Model.User;
import Request.LoginRequest;
import Result.LoginResult;

import java.sql.Connection;
import java.util.Random;

/**
 * A class to log user and generate authToken
 */
public class Login {
    private Database database = new Database();
    UserDao userDao;
    AuthTokenDao authTokenDao;

    public Login() throws DataAccessException {}

    /**
     * This begins the login process
     *
     * @param r The request values attached to the new login of a user
     * @return The result of logging in a user
     */
    public LoginResult login(LoginRequest r) throws DataAccessException {
        Connection conn = database.openConnection();
        userDao = new UserDao(conn);
        authTokenDao = new AuthTokenDao(conn);

        LoginResult result = new LoginResult();
        AuthToken authToken = new AuthToken();
        String username = r.getUsername();
        String password = r.getPassword();

        try {
            if ((username == null) || (password == null)) {
                result.setMessage("Error: Invalid input");
                result.setSuccess(false);
                return result;
            }
            User currUser = userDao.find(username);
            if (currUser == null) {
                result.setMessage("Error: Username does not exist");
                result.setSuccess(false);
                database.closeConnection(false);
                return result;
            }
            else if (!currUser.getPassword().equals(password)) {
                result.setMessage("Error: Incorrect Password");
                result.setSuccess(false);
                database.closeConnection(false);
                return result;
            }
            else {
                authToken.setUsername(username);
                authToken.setAuthToken(getRandString());
                authTokenDao.insert(authToken);
                result.setAuthToken(authToken.getAuthToken());
                result.setUsername(authToken.getUsername());
                result.setPersonID(currUser.getPersonID());
                result.setSuccess(true);
                database.closeConnection(true);
                return result;
            }
        } catch (DataAccessException e) {
            database.closeConnection(false);
            throw new DataAccessException("SQL ERROR");
        }
    }

    private String getRandString() {
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
