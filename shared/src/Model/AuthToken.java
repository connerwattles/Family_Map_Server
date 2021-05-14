package Model;

/**
 * An authorization token
 */
public class AuthToken {
    /**
     * The authorization token the user will receive
     */
    public String authToken = null;
    /**
     * The username of the user this auth token will be attached to
     */
    public String username = null;

    /**
     *
     * @return The auth token
     */
    public String getAuthToken() { return authToken; }

    /**
     *
     * @param authToken The new auth token
     */
    public void setAuthToken(String authToken) { this.authToken = authToken; }

    /**
     *
     * @return The username of this auth token's user
     */
    public String getUsername() { return username; }

    /**
     *
     * @param username The new username for this auth token's user
     */
    public void setUsername(String username) { this.username = username; }

    @Override
    public String toString() {
        return "AuthToken{" +
                "authToken='" + authToken + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
