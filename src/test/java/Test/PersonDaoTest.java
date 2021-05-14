package Test;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.PersonDao;
import DataAccess.UserDao;
import Model.Event;
import Model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.util.ArrayList;

public class PersonDaoTest {
    private PersonDao personDao;
    private Database database;
    Person personTest = new Person();

    @BeforeEach
    public void setUp() throws DataAccessException {
        Connection conn;

        database = new Database();
        conn = database.openConnection();
        personDao = new PersonDao(conn);

        database.clearTables();
        database.closeConnection(true);
        conn = database.openConnection();
        personDao = new PersonDao(conn);
    }

    @AfterEach
    public void reset() throws DataAccessException {
        database.closeConnection(false);
        database = null;
        personDao = null;
    }

    private void createPerson() {
        personTest.setPersonID("randID");
        personTest.setUsername("connerw");
        personTest.setFirstName("conner");
        personTest.setLastName("wattles");
        personTest.setGender("m");
        personTest.setFatherID("fatherID");
        personTest.setMotherID("motherID");
        personTest.setSpouseID("spouseID");
    }

    @Test
    public void testPersonInsertFind() {
        try {
            createPerson();

            personDao.insert(personTest);

            assertEquals(personTest.toString(), personDao.find("randID").toString());
        } catch (DataAccessException e) {
            fail("Data Access Exception");
        }
    }

    @Test
    public void negativeTestPersonInsert() {
        try {
            createPerson();

            personDao.insert(personTest);
        } catch (DataAccessException e) {
            fail("DataAccessException");
        }
        Person temp = new Person();
        temp.setPersonID("randID");
        temp.setUsername("temp");
        temp.setFirstName("temp");
        temp.setLastName("temp");
        temp.setGender("m");
        temp.setFatherID("temp");
        temp.setMotherID("temp");
        temp.setSpouseID("temp");
        assertThrows(DataAccessException.class, ()-> personDao.insert(temp));
    }

    @Test
    public void negativeTestUserFind() {
        try {
            createPerson();

            personDao.insert(personTest);

            assertEquals(null, personDao.find("nonexistent"));
        } catch (DataAccessException e) {
            fail("Data Access Exception");
        }
    }

    @Test
    public void testPersonDelete() {
        try {
            createPerson();

            personDao.insert(personTest);
            database.closeConnection(true);
            Connection conn = database.openConnection();
            personDao = new PersonDao(conn);
            personDao.delete(personTest);
            assertEquals(null, personDao.find("randInd"));
        } catch (DataAccessException e) {
            fail("Data Access Exception");
        }
    }

    @Test
    public void positiveAllPersons() {
        try {
            createPerson();
            Person person2 = new Person();
            person2.setPersonID("randID2");
            person2.setUsername("connerw");
            person2.setFirstName("conner2");
            person2.setLastName("wattles2");
            person2.setGender("m");
            person2.setFatherID("fatherID2");
            person2.setMotherID("motherID2");
            person2.setSpouseID("spouseID2");



            personDao.insert(personTest);
            personDao.insert(person2);

            ArrayList<Person> persons = personDao.allPersons("connerw");

            assertEquals(persons.size(), 2);

        } catch (DataAccessException e) {
            fail("Data Access Exception");
        }
    }

    @Test
    public void negativeAllPersons() {
        try {
            createPerson();
            Person person2 = new Person();
            person2.setPersonID("randID2");
            person2.setUsername("connerw2");
            person2.setFirstName("conner2");
            person2.setLastName("wattles2");
            person2.setGender("m");
            person2.setFatherID("fatherID2");
            person2.setMotherID("motherID2");
            person2.setSpouseID("spouseID2");



            personDao.insert(personTest);
            personDao.insert(person2);

            ArrayList<Person> persons = personDao.allPersons("connerw");

            assertNotEquals(persons.size(), 2);

        } catch (DataAccessException e) {
            fail("Data Access Exception");
        }
    }
}
