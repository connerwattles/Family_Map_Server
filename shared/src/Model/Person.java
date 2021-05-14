package Model;

/**
 * A Person
 */
public class Person {
    /**
     * The ID for this person
     */
    public String personID = null;
    /**
     * The username for this person
     */
    public String associatedUsername = null;
    /**
     * The first name of this person
     */
    public String firstName = null;
    /**
     * The last name of this person
     */
    public String lastName = null;
    /**
     * The gender (m or f) of this person
     */
    public String gender = null;
    /**
     * The ID of this person's father
     */
    public String fatherID = null;
    /**
     * The ID of this person's mother
     */
    public String motherID = null;
    /**
     * The ID of this person's spouse
     */
    public String spouseID = null;

    /**
     *
     * @return This person's ID
     */
    public String getPersonID() { return personID; }

    /**
     *
     * @param personID The new ID for this person
     */
    public void setPersonID(String personID) { this.personID = personID; }

    /**
     *
     * @return This person's username
     */
    public String getUsername() { return associatedUsername; }

    /**
     *
     * @param username The new username for this person
     */
    public void setUsername(String username) { this.associatedUsername = username; }

    /**
     *
     * @return This person's first name
     */
    public String getFirstName() { return firstName; }

    /**
     *
     * @param firstName The new first name for this person
     */
    public void setFirstName(String firstName) { this.firstName = firstName; }

    /**
     *
     * @return The last name for this person
     */
    public String getLastName() { return lastName; }

    /**
     *
     * @param lastName The new last name for this person
     */
    public void setLastName(String lastName) { this.lastName = lastName; }

    /**
     *
     * @return The gender m or f of this person
     */
    public String getGender() { return gender; }

    /**
     *
     * @param gender The gender for this person
     */
    public void setGender(String gender) { this.gender = gender; }

    /**
     *
     * @return The ID for this person's father
     */
    public String getFatherID() { return fatherID; }

    /**
     *
     * @param fatherID The new ID for this person's father
     */
    public void setFatherID(String fatherID) { this.fatherID = fatherID; }

    /**
     *
     * @return The ID for this person's mother
     */
    public String getMotherID() { return motherID; }

    /**
     *
     * @param motherID The new ID for this person's mother
     */
    public void setMotherID(String motherID) { this.motherID = motherID; }

    /**
     *
     * @return The ID for this person's spouse
     */
    public String getSpouseID() { return spouseID; }

    /**
     *
     * @param spouseID The new ID for this person's spouse
     */
    public void setSpouseID(String spouseID) { this.spouseID = spouseID; }

    @Override
    public String toString() {
        return "Person{" +
                "personID='" + personID + '\'' +
                ", username='" + associatedUsername + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender='" + gender + '\'' +
                ", fatherID='" + fatherID + '\'' +
                ", motherID='" + motherID + '\'' +
                ", spouseID='" + spouseID + '\'' +
                '}';
    }
}
