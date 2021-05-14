package DataAccess;

import Model.Event;
import Model.Person;

import java.sql.*;
import java.util.ArrayList;

/**
 * A data access object for event
 */
public class EventDao {
    private final Connection conn;

    public EventDao(Connection conn) { this.conn = conn; }

    /**
     * This method will insert elements for an event into the event table
     *
     * @param event The event object we want values from to insert into the table
     */
    public void insert(Event event) throws DataAccessException {
        String sql = "INSERT INTO Events (EventID, Username, PersonID, Latitude, Longitude, " +
                "Country, City, EventType, Year) VALUES(?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, event.getEventID());
            stmt.setString(2, event.getUsername());
            stmt.setString(3, event.getPersonID());
            stmt.setFloat(4, event.getLatitude());
            stmt.setFloat(5, event.getLongitude());
            stmt.setString(6, event.getCountry());
            stmt.setString(7, event.getCity());
            stmt.setString(8, event.getEventType());
            stmt.setInt(9, event.getYear());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting into the database");
        }
    }

    /**
     * This method will read values from the event table
     *
     * @param eventID The event we want associated values from to read from the table
     * @return An event object with contents of from the table
     */
    public Event find(String eventID) throws DataAccessException {
        Event event;
        ResultSet rs = null;
        String sql = "SELECT * FROM Events WHERE EventID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, eventID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                event = new Event();
                event.setEventID(rs.getString("EventID"));
                event.setUsername(rs.getString("Username"));
                event.setPersonID(rs.getString("PersonID"));
                event.setLatitude(rs.getFloat("Latitude"));
                event.setLongitude(rs.getFloat("Longitude"));
                event.setCountry(rs.getString("Country"));
                event.setCity(rs.getString("City"));
                event.setEventType(rs.getString("EventType"));
                event.setYear(rs.getInt("Year"));
                return event;
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
     * This method will delete values from the event table
     *
     * @param event The event we want associated values from to delete from the table
     */
    public void delete(Event event) throws DataAccessException {
        String eventID = event.getEventID();
        try (Statement stmt = conn.createStatement()){
            String sql = "DELETE FROM Events WHERE EventID = " + eventID;
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DataAccessException("SQL Error encountered while clearing tables");
        }
    }

    public ArrayList<Event> allEvents(String username) throws DataAccessException {
        ArrayList<Event> events = new ArrayList<Event>();
        ResultSet rs = null;
        String sql = "SELECT * FROM Events WHERE Username = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Event event = new Event();
                event.setEventID(rs.getString("EventID"));
                event.setUsername(rs.getString("Username"));
                event.setPersonID(rs.getString("PersonID"));
                event.setLatitude(rs.getFloat("Latitude"));
                event.setLongitude(rs.getFloat("Longitude"));
                event.setCountry(rs.getString("Country"));
                event.setCity(rs.getString("City"));
                event.setEventType(rs.getString("EventType"));
                event.setYear(rs.getInt("Year"));
                events.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding people");
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        return events;
    }
}
