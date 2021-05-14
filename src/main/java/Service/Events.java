package Service;

import DataAccess.AuthTokenDao;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.EventDao;
import Model.AuthToken;
import Model.Event;
import Model.Person;
import Request.EventsRequest;
import Result.EventsResult;

import java.sql.Connection;
import java.util.ArrayList;

/**
 * This class returns either a single event object
 * or an array of event objects
 */
public class Events {
    private Database database = new Database();
    private EventDao eventDao;
    private AuthTokenDao authTokenDao;

    public Events() throws DataAccessException {
    }

    /**
     * Begins the events service of returning event objects
     *
     * @param r The request values are either an event ID or an auth token
     * @return Either the data of one event object or an array of event objects
     */
    public EventsResult event(EventsRequest r) throws DataAccessException {
        Connection conn = database.openConnection();
        eventDao = new EventDao(conn);
        authTokenDao = new AuthTokenDao(conn);

        String token = r.getAuthToken();
        String eventID = r.getEventID();
        EventsResult result = new EventsResult();

        if (eventID != null) {
            try {
                Event foundEvent = eventDao.find(eventID);
                AuthToken foundToken = authTokenDao.findFromToken(token);
                if (foundEvent == null) {
                    result.setMessage("Error: Event not found");
                    result.setSuccess(false);
                    database.closeConnection(false);
                }
                else if (!foundEvent.getUsername().equals(foundToken.getUsername())) {
                    result.setMessage("Error: Given EventID not associated with token");
                    result.setSuccess(false);
                    database.closeConnection(false);
                }
                else {
                    result.setUsername(foundEvent.getUsername());
                    result.setPersonID(foundEvent.getPersonID());
                    result.setEventID(foundEvent.getEventID());
                    result.setLatitude(foundEvent.getLatitude());
                    result.setLongitude(foundEvent.getLongitude());
                    result.setCountry(foundEvent.getCountry());
                    result.setCity(foundEvent.getCity());
                    result.setEventType(foundEvent.getEventType());
                    result.setYear(foundEvent.getYear());
                    result.setSuccess(true);
                    database.closeConnection(true);
                }
                return result;
            } catch (DataAccessException e) {
                result.setMessage("Error: Data Access Error");
                result.setSuccess(false);
                database.closeConnection(false);
                return result;
            }
        }
        else {
            try {
                AuthToken authToken = authTokenDao.findFromToken(token);
                if (authToken == null) {
                    result.setMessage("Error: Invalid Auth Token");
                    result.setSuccess(false);
                    database.closeConnection(false);
                    return result;
                }
                else {
                    ArrayList<Event> allEvents = eventDao.allEvents(authToken.getUsername());
                    if (allEvents.size() <= 0) {
                        result.setMessage("Error: No Events");
                        result.setSuccess(false);
                        database.closeConnection(false);
                        return result;
                    }
                    else {
                        //How to convert an ArrayList to Array
                        Event[] event = allEvents.toArray(new Event[0]);
                        result.setData(event);
                        result.setSuccess(true);
                        database.closeConnection(true);
                        return result;
                    }
                }
            } catch (DataAccessException e) {
                result.setMessage("Error: Data Access Error");
                result.setSuccess(false);
                database.closeConnection(false);
                return result;
            }
        }
    }
}
