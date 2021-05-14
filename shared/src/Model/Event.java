package Model;

/**
 * An event
 */
public class Event {
    /**
     * The ID for this event
     */
    public String eventID = null;
    /**
     * The username of the user using this event
     */
    public String associatedUsername = null;
    /**
     * The ID of the person attached to this event
     */
    public String personID = null;
    /**
     * The latitude of the location of this event
     */
    public Float latitude = null;
    /**
     * The longitude of the location of this event
     */
    public Float longitude = null;
    /**
     * The country this event occurred in
     */
    public String country = null;
    /**
     * The city this event occurred in
     */
    public String city = null;
    /**
     * The type of event this event is
     */
    public String eventType = null;
    /**
     * The year this event occurred in
     */
    public Integer year = null;

    /**
     *
     * @return The ID of this event
     */
    public String getEventID() { return eventID; }

    /**
     *
     * @param eventID The new ID of this event
     */
    public void setEventID(String eventID) { this.eventID = eventID; }

    /**
     *
     * @return The username of the user using this event
     */
    public String getUsername() { return associatedUsername; }

    /**
     *
     * @param username The new username of the user using this event
     */
    public void setUsername(String username) { this.associatedUsername = username; }

    /**
     *
     * @return The ID of the person attached to this event
     */
    public String getPersonID() { return personID; }

    /**
     *
     * @param personID The new ID of the person attached to this event
     */
    public void setPersonID(String personID) { this.personID = personID; }

    /**
     *
     * @return The latitude of the event
     */
    public float getLatitude() { return latitude; }

    /**
     *
     * @param latitude The new latitude of the event
     */
    public void setLatitude(float latitude) { this.latitude = latitude; }

    /**
     *
     * @return The longitude of the event
     */
    public float getLongitude() { return longitude; }

    /**
     *
     * @param longitude The new longitude of the event
     */
    public void setLongitude(float longitude) { this.longitude = longitude; }

    /**
     *
     * @return The country of the event
     */
    public String getCountry() { return country; }

    /**
     *
     * @param country The new country of the event
     */
    public void setCountry(String country) { this.country = country; }

    /**
     *
     * @return The city of the event
     */
    public String getCity() { return city; }

    /**
     *
     * @param city The new city of the event
     */
    public void setCity(String city) { this.city = city; }

    /**
     *
     * @return The event type of the event
     */
    public String getEventType() { return eventType; }

    /**
     *
     * @param eventType The new event type of the event
     */
    public void setEventType(String eventType) { this.eventType = eventType; }

    /**
     *
     * @return The year of the event
     */
    public int getYear() { return year; }

    /**
     *
     * @param year The new year of the event
     */
    public void setYear(int year) { this.year = year; }

    @Override
    public String toString() {
        return "Event{" +
                "eventID='" + eventID + '\'' +
                ", associatedUsername='" + associatedUsername + '\'' +
                ", personID='" + personID + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", eventType='" + eventType + '\'' +
                ", year=" + year +
                '}';
    }
}
