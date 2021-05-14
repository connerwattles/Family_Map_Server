package Result;

import Model.Event;

import java.util.HashSet;
import java.util.Set;

/**
 * The contents for response of events service
 */
public class EventsResult {
    /**
     * The event ID for one event
     */
    String eventID = null;
    /**
     * The username of the user attached to one event
     */
    String associatedUsername = null;
    /**
     * the person ID of the person attached to one event
     */
    String personID = null;
    /**
     * the latitude of one event
     */
    float latitude = -1;
    /**
     * The longitude of one event
     */
    float longitude = -1;
    /**
     * the country of the one event
     */
    String country = null;
    /**
     * the city of the one event
     */
    String city = null;
    /**
     * the event type of the one event
     */
    String eventType = null;
    /**
     * the year the one event occurred
     */
    int year = 0;

    Event[] data;

    /**
     * The message as a result of the event service
     */
    String message = null;
    /**
     * the successfulness of the event service
     */
    boolean success;

    /**
     *
     * @return the event ID of the one specific event
     */
    public String getEventID() { return eventID; }

    /**
     *
     * @param eventID new event ID for the one specific event
     */
    public void setEventID(String eventID) { this.eventID = eventID; }

    /**
     *
     * @return the username of the one specific event
     */
    public String getUsername() { return associatedUsername; }

    /**
     *
     * @param username new username for the one specific event
     */
    public void setUsername(String username) { this.associatedUsername = username; }

    /**
     *
     * @return the person ID of the one specific event
     */
    public String getPersonID() { return personID; }

    /**
     *
     * @param personID new person ID for the one specific event
     */
    public void setPersonID(String personID) { this.personID = personID; }

    /**
     *
     * @return the latitude of the one specific event
     */
    public float getLatitude() { return latitude; }

    /**
     *
     * @param latitude new latitude for the one specific event
     */
    public void setLatitude(float latitude) { this.latitude = latitude; }

    /**
     *
     * @return the longitude of the one specific event
     */
    public float getLongitude() { return longitude; }

    /**
     *
     * @param longitude new longitude for the one specific event
     */
    public void setLongitude(float longitude) { this.longitude = longitude; }

    /**
     *
     * @return the country of the one specific event
     */
    public String getCountry() { return country; }

    /**
     *
     * @param country new country for the one specific event
     */
    public void setCountry(String country) { this.country = country; }

    /**
     *
     * @return the city of the one specific event
     */
    public String getCity() { return city; }

    /**
     *
     * @param city new city for the one specific event
     */
    public void setCity(String city) { this.city = city; }

    /**
     *
     * @return the event type of the one specific event
     */
    public String getEventType() { return eventType; }

    /**
     *
     * @param eventType new event type for the one specific event
     */
    public void setEventType(String eventType) { this.eventType = eventType; }

    /**
     *
     * @return the year of the one specific event
     */
    public int getYear() { return year; }

    /**
     *
     * @param year new year for the one specific event
     */
    public void setYear(int year) { this.year = year; }

    public Event[] getData() { return data; }

    public void setData(Event[] data) { this.data = data; }

    /**
     *
     * @return the message for the event service
     */
    public String getMessage() { return message; }

    /**
     *
     * @param message  new message for the one specific event
     */
    public void setMessage(String message) { this.message = message; }

    /**
     *
     * @return the successfulness of the event service
     */
    public boolean getSuccess() { return success; }

    /**
     *
     * @param success new successfulness of the event service
     */
    public void setSuccess(boolean success) { this.success = success; }

    public Set<Event> getDataAsSet() {
        Set<Event> temp = new HashSet<Event>();
        for (Event e : data) {
            temp.add(e);
        }

        //Set<Event> temp = new HashSet<Event>(Arrays.asList(data));
        return temp;
    }
}
