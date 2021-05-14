package Test;

import DataAccess.*;
import Model.Event;
import Model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.util.ArrayList;

public class EventDaoTest {
    private EventDao eventDao;
    private Database database;
    Event eventTest = new Event();

    @BeforeEach
    public void setUp() throws DataAccessException {
        Connection conn;

        database = new Database();
        conn = database.openConnection();
        eventDao = new EventDao(conn);

        database.clearTables();
        database.closeConnection(true);
        conn = database.openConnection();
        eventDao = new EventDao(conn);
    }

    @AfterEach
    public void reset() throws DataAccessException {
        database.closeConnection(false);
        database = null;
        eventDao = null;
    }

    private void createEvent() {
        eventTest.setEventID("eventID");
        eventTest.setPersonID("randID");
        eventTest.setUsername("username");
        eventTest.setLatitude(100);
        eventTest.setLongitude(100);
        eventTest.setCountry("USA");
        eventTest.setCity("Provo");
        eventTest.setEventType("birth");
        eventTest.setYear(2020);
    }

    @Test
    public void testEventInsertFind() {
        try {
            createEvent();

            eventDao.insert(eventTest);

            assertEquals(eventTest.toString(), eventDao.find("eventID").toString());
        } catch (DataAccessException e) {
            fail("Data Access Exception");
        }
    }

    @Test
    public void negativeTestEventInsert() {
        try {
            createEvent();

            eventDao.insert(eventTest);
        } catch (DataAccessException e) {
            fail("DataAccessException");
        }
        Event temp = new Event();
        temp.setEventID("eventID");
        temp.setPersonID("temp");
        temp.setUsername("temp");
        temp.setLatitude(10);
        temp.setLongitude(10);
        temp.setCountry("temp");
        temp.setCity("temp");
        temp.setEventType("temp");
        temp.setYear(10);
        assertThrows(DataAccessException.class, ()-> eventDao.insert(temp));
    }

    @Test
    public void negativeTestEventFind() {
        try {
            createEvent();

            eventDao.insert(eventTest);

            assertEquals(null, eventDao.find("nonexistent"));
        } catch (DataAccessException e) {
            fail("Data Access Exception");
        }
    }

    @Test
    public void testEventDelete() {
        try {
            createEvent();

            eventDao.insert(eventTest);
            database.closeConnection(true);
            Connection conn = database.openConnection();
            eventDao = new EventDao(conn);
            eventDao.delete(eventTest);
            assertEquals(null, eventDao.find("randInd"));
        } catch (DataAccessException e) {
            fail("Data Access Exception");
        }
    }

    @Test
    public void positiveAllEvents() {
        try {
            createEvent();
            Event event2 = new Event();
            event2.setEventID("eventID2");
            event2.setPersonID("randID2");
            event2.setUsername("username");
            event2.setLatitude(200);
            event2.setLongitude(200);
            event2.setCountry("Canada");
            event2.setCity("Toronto");
            event2.setEventType("birth");
            event2.setYear(2019);


            eventDao.insert(eventTest);
            eventDao.insert(event2);

            ArrayList<Event> events = eventDao.allEvents("username");

            assertEquals(events.size(), 2);

        } catch (DataAccessException e) {
            fail("Data Access Exception");
        }
    }

    @Test
    public void negativeAllEvents() {
        try {
            createEvent();
            Event event2 = new Event();
            event2.setEventID("eventID2");
            event2.setPersonID("randID2");
            event2.setUsername("username2");
            event2.setLatitude(200);
            event2.setLongitude(200);
            event2.setCountry("Canada");
            event2.setCity("Toronto");
            event2.setEventType("birth");
            event2.setYear(2019);


            eventDao.insert(eventTest);
            eventDao.insert(event2);

            ArrayList<Event> events = eventDao.allEvents("username");

            assertNotEquals(events.size(), 2);

        } catch (DataAccessException e) {
            fail("Data Access Exception");
        }
    }
}
