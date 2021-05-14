package Request;

/**
 * The auth token or event ID needed for events service
 */
public class EventsRequest {
    /**
     * The auth token from the users session
     */
    String authToken = null;
    /**
     * The event ID for the event information
     */
    String eventID = null;

    /**
     *
     * @return The authToken for the event service
     */
    public String getAuthToken() { return authToken; }

    /**
     *
     * @param authToken The new authToken for the service
     */
    public void setAuthToken(String authToken) {this.authToken = authToken; }

    /**
     *
     * @return The event ID for the service
     */
    public String getEventID() { return eventID; }

    /**
     *
     * @param eventID The new event ID for the service
     */
    public void setEventID(String eventID) { this.eventID = eventID; }
}
