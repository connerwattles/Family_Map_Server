package DataAccess;

import Model.Person;
import Model.User;

import java.sql.*;
import java.util.ArrayList;

/**
 * A data access object for person
 */
public class PersonDao {
    private final Connection conn;

    public PersonDao(Connection conn) {
        this.conn = conn;
    }

    /**
     * This method will insert values into the person table
     *
     * @param person The person object we want to insert values from
     */
    public void insert(Person person) throws DataAccessException {
        String sql = "INSERT INTO Persons (PersonID, Username, FirstName, LastName, Gender, FatherID, MotherID, SpouseID)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, person.getPersonID());
            stmt.setString(2, person.getUsername());
            stmt.setString(3, person.getFirstName());
            stmt.setString(4, person.getLastName());
            stmt.setString(5, person.getGender());
            stmt.setString(6, person.getFatherID());
            stmt.setString(7, person.getMotherID());
            stmt.setString(8, person.getSpouseID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting into the database");
        }
    }

    /**
     * This method will read values from the person table
     *
     * @param personID The person we want associated values to be read
     * @return A person object with contents from the table
     */
    public Person find(String personID) throws DataAccessException {
        Person person;
        ResultSet rs = null;
        String sql = "SELECT * FROM Persons WHERE PersonID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, personID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                person = new Person();
                person.setPersonID(rs.getString("PersonID"));
                person.setUsername(rs.getString("Username"));
                person.setFirstName(rs.getString("FirstName"));
                person.setLastName(rs.getString("LastName"));
                person.setGender(rs.getString("Gender"));
                person.setFatherID(rs.getString("FatherID"));
                person.setMotherID(rs.getString("MotherID"));
                person.setSpouseID(rs.getString("SpouseID"));
                return person;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding person");
        } finally {
            if (rs != null) {
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
     * This method will delete values in the person table
     *
     * @param p The person we want associated values to be deleted
     */
    public void delete(Person p) throws DataAccessException {
        String personID = p.getPersonID();
        try (Statement stmt = conn.createStatement()) {
            String sql = "DELETE FROM Persons WHERE PersonID = " + "personID";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DataAccessException("SQL Error encountered while clearing tables");
        }
        p = null;
    }

    public ArrayList<Person> allPersons(String username) throws DataAccessException {
        ArrayList<Person> people = new ArrayList<Person>();
        ResultSet rs = null;
        String sql = "SELECT * FROM Persons WHERE Username = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Person person = new Person();
                person.setPersonID(rs.getString("PersonID"));
                person.setUsername(rs.getString("Username"));
                person.setFirstName(rs.getString("FirstName"));
                person.setLastName(rs.getString("LastName"));
                person.setGender(rs.getString("Gender"));
                person.setFatherID(rs.getString("FatherID"));
                person.setMotherID(rs.getString("MotherID"));
                person.setSpouseID(rs.getString("SpouseID"));
                people.add(person);
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
        return people;
    }
}

