package DataAccess;

import Model.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class ClearBasedUsername {
    private final Connection conn;

    public ClearBasedUsername(Connection conn) { this.conn = conn; }

    public void delete(String username) throws DataAccessException {
        String sql = "DELETE FROM Persons WHERE Username = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.executeUpdate();
        } catch(SQLException e) {
            throw new DataAccessException("SQL Error encountered while clearing tables");
        }
        String sql1 = "DELETE FROM Events WHERE Username = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql1)) {
            stmt.setString(1, username);
            stmt.executeUpdate();
        } catch(SQLException e) {
            throw new DataAccessException("SQL Error encountered while clearing tables");
        }
    }
}
