package DataAccess;

import Model.AuthToken;
import Model.User;

import java.sql.*;

/**
 * A data access object for authToken
 */
public class AuthTokenDao {
    private final Connection conn;

    public AuthTokenDao(Connection conn) { this.conn = conn; }
    /**
     * This method will insert values from an authToken object to be put in the authToken table
     *
     * @param t The authToken object we want to get values from to put into the table
     */
    public void insert(AuthToken t) throws DataAccessException {
        String sql = "INSERT INTO AuthToken (Username, Token)" +
                "VALUES (?, ?);";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, t.getUsername());
            stmt.setString(2, t.getAuthToken());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting into the database");
        }
    }

    /**
     * This method will read values from the authToken table
     *
     * @param username The authToken object we want associated values from to read from the table
     * @return An authToken object with contents from the table
     */
    public AuthToken find(String username) throws DataAccessException {
        AuthToken authToken;
        ResultSet rs = null;
        String sql = "SELECT * FROM AuthToken WHERE Username = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            if (rs.next()) {
                authToken = new AuthToken();
                authToken.setUsername(rs.getString("Username"));
                authToken.setAuthToken(rs.getString("Token"));
                return authToken;
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

    public AuthToken findFromToken(String token) throws DataAccessException {
        AuthToken authToken;
        ResultSet rs = null;
        String sql = "SELECT * FROM AuthToken WHERE Token = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, token);
            rs = stmt.executeQuery();
            if (rs.next()) {
                authToken = new AuthToken();
                authToken.setUsername(rs.getString("Username"));
                authToken.setAuthToken(rs.getString("Token"));
                return authToken;
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
     * This method will delete values from the authToken table
     *
     * @param t The authToken object we want associated values from to delete from the table
     */
    public void delete(AuthToken t) throws DataAccessException {
        String username = t.getUsername();
        try (Statement stmt = conn.createStatement()){
            String sql = "DELETE FROM AuthToken WHERE Username = " + "username";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DataAccessException("SQL Error encountered while clearing tables");
        }
        t = null;
    }
}
