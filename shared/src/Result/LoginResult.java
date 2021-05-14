package Result;

/**
 * The contents of the result of login service
 */
public class LoginResult {
    /**
     * The authToken in the response
     */
    String authtoken = null;
    /**
     * The username in the response
     */
    String username = null;
    /**
     * The personID in the response
     */
    String personID = null;
    /**
     * The message response
     */
    String message = null;
    /**
     * Whether the service was successful or not
     */
    boolean success;

    /**
     * @return response's authToken
     */
    public String getAuthToken() { return authtoken; }

    /**
     * @param authToken New response authToken
     */
    public void setAuthToken(String authToken) { this.authtoken = authToken; }

    /**
     * @return response's username
     */
    public String getUsername() { return username; }

    /**
     * @param username New response username
     */
    public void setUsername(String username) { this.username = username; }

    /**
     * @return response's person ID
     */
    public String getPersonID() { return personID; }

    /**
     * @param personID new response person ID
     */
    public void setPersonID(String personID) { this.personID = personID; }

    /**
     * @return response message
     */
    public String getMessage() { return message; }

    /**
     * @param message new response message
     */
    public void setMessage(String message) { this.message = message; }

    /**
     * @return success of service
     */
    public boolean getSuccess() { return success; }

    /**
     * @param success new success of service
     */
    public void setSuccess(boolean success) { this.success = success; }
}
