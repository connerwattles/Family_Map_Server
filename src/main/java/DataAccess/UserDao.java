package DataAccess;

import Model.Event;
import Model.User;

import java.io.File;
import java.sql.*;

/**
 * A data access object for user
 */
public class UserDao {
    private final Connection conn;

    public UserDao(Connection conn) { this.conn = conn; }

    /**
     * This method will insert values into the user database table
     *
     * @param user The current user object we want to insert into the table
     * @throws SQLException Database access errors
     */
    public void insert(User user) throws DataAccessException {
        String sql = "INSERT INTO Users (Username, Password, Email, FirstName, LastName, Gender, PersonID)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?);";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getFirstName());
            stmt.setString(5, user.getLastName());
            stmt.setString(6, user.getGender());
            stmt.setString(7, user.getPersonID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting into the database");
        }
    }

    /**
     * This method will read values from the user table
     *
     * @param username The current user object we want values associated to
     * @return A user object with contents from the table
     */
    public User find(String username) throws DataAccessException {
        User user;
        ResultSet rs = null;
        String sql = "SELECT * FROM Users WHERE Username = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setUsername(rs.getString("Username"));
                user.setPassword(rs.getString("Password"));
                user.setEmail(rs.getString("Email"));
                user.setFirstName(rs.getString("FirstName"));
                user.setLastName(rs.getString("LastName"));
                user.setGender(rs.getString("Gender"));
                user.setPersonID(rs.getString("PersonID"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding event");
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        return null;
    }

    /**
     * This method will delete values from the user table
     *
     * @param u The user that associated values in the table should be deleted
     */
    public void delete(User u) throws DataAccessException {
        String username = u.getUsername();
        try (Statement stmt = conn.createStatement()){
            String sql = "DELETE FROM Users WHERE Username = " + "username";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DataAccessException("SQL Error encountered while clearing tables");
        }
        u = null;
    }
}
