package Request;

/**
 * The auth token or personID to supply the persons service
 */
public class PersonsRequest {
    /**
     * The authToken from the users session
     */
    String authToken = null;
    /**
     * The personID to get the persons information
     */
    String personID = null;

    /**
     *
     * @return The authToken
     */
    public String getAuthToken() { return authToken; }

    /**
     *
     * @param authToken The new auth Token for the service
     */
    public void setAuthToken(String authToken) { this.authToken = authToken; }

    /**
     *
     * @return The person ID for the service
     */
    public String getPersonID() { return personID; }

    /**
     *
     * @param personID The new person ID for the service
     */
    public void setPersonID(String personID) { this.personID = personID; }
}
