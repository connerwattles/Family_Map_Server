package Model;

/**
 * A user
 */
public class User {
    /**
     * Users username
     */
    public String username = null;
    /**
     * Users password
     */
    public String password = null;
    /**
     * Users email
     */
    public String email = null;
    /**
     * Users first name
     */
    public String firstName = null;
    /**
     * Users last name
     */
    public String lastName = null;
    /**
     * Users gender
     */
    public String gender = null;
    /**
     * Users ID attached to their person
     */
    public String personID = null;

    /**
     *
     * @return the users username
     */
    public String getUsername() { return username; }

    /**
     *
     * @param username The new username for the user
     */
    public void setUsername(String username) { this.username = username; }

    /**
     *
     * @return users password
     */
    public String getPassword() { return password; }

    /**
     *
     * @param password The new password for the user
     */
    public void setPassword(String password) { this.password = password; }

    /**
     *
     * @return users email
     */
    public String getEmail() { return email; }

    /**
     *
     * @param email The new email for the user
     */
    public void setEmail(String email) { this.email = email; }

    /**
     *
     * @return users first name
     */
    public String getFirstName() { return firstName; }

    /**
     *
     * @param firstName The new first name for the user
     */
    public void setFirstName(String firstName) { this.firstName = firstName; }

    /**
     *
     * @return users last name
     */
    public String getLastName() { return lastName; }

    /**
     *
     * @param lastName The new last name for the user
     */
    public void setLastName(String lastName) { this.lastName = lastName; }

    /**
     *
     * @return users gender m or f
     */
    public String getGender() { return gender; }

    /**
     *
     * @param gender The new gender for the user m or f
     */
    public void setGender(String gender) { this.gender = gender; }

    /**
     *
     * @return users ID attached to their person
     */
    public String getPersonID() { return personID; }

    /**
     *
     * @param personID The new ID for the users person
     */
    public void setPersonID(String personID) { this.personID = personID; }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender='" + gender + '\'' +
                ", personID='" + personID + '\'' +
                '}';
    }
}
