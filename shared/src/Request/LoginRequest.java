package Request;

/**
 * The username and password needed for login service
 */
public class LoginRequest {
    /**
     * User's username that is logging in
     */
    String username = null;
    /**
     * User's password that is logging in
     */
    String password = null;

    /**
     *
     * @return Login service username
     */
    public String getUsername() { return username; }

    /**
     *
     * @param username New login service username
     */
    public void setUsername(String username) { this.username = username; }

    /**
     *
     * @return Login service password
     */
    public String getPassword() { return password; }

    /**
     *
     * @param password New login service password
     */
    public void setPassword(String password) { this.password = password; }
}
