package Request;

/**
 * The user information needed for request service
 */
public class RegisterRequest {
    /**
     * Username for request
     */
    String username = null;
    /**
     * Password for request
     */
    String password = null;
    /**
     * Email for request
     */
    String email = null;
    /**
     * First name for request
     */
    String firstName = null;
    /**
     * Last name for request
     */
    String lastName = null;
    /**
     * Gender for request
     */
    String gender = null;

    /**
     *
     * @return username
     */
    public String getUsername() { return username; }

    /**
     *
     * @param username The new username
     */
    public void setUsername(String username) { this.username = username; }

    /**
     *
     * @return password
     */
    public String getPassword() { return password; }

    /**
     *
     * @param password The new password
     */
    public void setPassword(String password) { this.password = password; }

    /**
     *
     * @return email
     */
    public String getEmail() { return email; }

    /**
     *
     * @param email The new email
     */
    public void setEmail(String email) { this.email = email; }

    /**
     *
     * @return first name
     */
    public String getFirstName() { return firstName; }

    /**
     *
     * @param firstName The new first name
     */
    public void setFirstName(String firstName) { this.firstName = firstName; }

    /**
     *
     * @return last name
     */
    public String getLastName() { return lastName; }

    /**
     *
     * @param lastName The new last name
     */
    public void setLastName(String lastName) { this.lastName = lastName; }

    /**
     *
     * @return gender m or f
     */
    public String getGender() { return gender; }

    /**
     *
     * @param gender The new gender m or f
     */
    public void setGender(String gender) { this.gender = gender; }
}
