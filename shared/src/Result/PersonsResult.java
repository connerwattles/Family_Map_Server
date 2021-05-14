package Result;

import Model.Person;

import java.util.HashSet;
import java.util.Set;

/**
 * The contents for the response of persons service
 */
public class PersonsResult {
    /**
     * Username for one person
     */
    String associatedUsername = null;
    /**
     * person ID for one person
     */
    String personID = null;
    /**
     * first name for one person
     */
    String firstName = null;
    /**
     * last name for one person
     */
    String lastName = null;
    /**
     * gender for one person
     */
    String gender = null;
    /**
     * the ID for the father of one person
     */
    String fatherID = null;
    /**
     * the ID for the mother of one person
     */
    String motherID = null;
    /**
     * the ID for the spouse of one person
     */
    String spouseID = null;
    /**
     * array of persons when a person ID is not given as a request
     */
    Person[] data;
    /**
     * the message for result of person service
     */
    String message = null;
    /**
     * the successfulness of the person service
     */
    boolean success;

    /**
     *
     * @return single person username
     */
    public String getUsername() { return associatedUsername; }

    /**
     *
     * @param username new single person username
     */
    public void setUsername(String username) { this.associatedUsername = username; }

    /**
     *
     * @return single person ID
     */
    public String getPersonID() { return personID; }

    /**
     *
     * @param personID new single person ID
     */
    public void setPersonID(String personID) { this.personID = personID; }

    /**
     *
     * @return single person first name
     */
    public String getFirstName() { return firstName; }

    /**
     *
     * @param firstName new single person first name
     */
    public void setFirstName(String firstName) { this.firstName = firstName; }

    /**
     *
     * @return single person last name
     */
    public String getLastName() { return lastName; }

    /**
     *
     * @param lastName new single person last name
     */
    public void setLastName(String lastName) { this.lastName = lastName; }

    /**
     *
     * @return single person gender
     */
    public String getGender() { return gender; }

    /**
     *
     * @param gender new single person gender
     */
    public void setGender(String gender) { this.gender = gender; }

    /**
     *
     * @return single person father ID
     */
    public String getFatherID() { return fatherID; }

    /**
     *
     * @param fatherID new single person father ID
     */
    public void setFatherID(String fatherID) { this.fatherID = fatherID; }

    /**
     *
     * @return single person mother ID
     */
    public String getMotherID() { return motherID; }

    /**
     *
     * @param motherID new single person mother ID
     */
    public void setMotherID(String motherID) { this.motherID = motherID; }

    /**
     *
     * @return single person spouse ID
     */
    public String getSpouseID() { return spouseID; }

    /**
     *
     * @param spouseID new single person spouse ID
     */
    public void setSpouseID(String spouseID) { this.spouseID = spouseID; }

    /**
     *
     * @return data of array of persons
     */
    public Person[] getData() { return data; }

    /**
     *
     * @param data new array of persons
     */
    public void setData(Person[] data) { this.data = data; }

    /**
     *
     * @return message of result of person service
     */
    public String getMessage() { return message; }

    /**
     *
     * @param message new message of result of person service
     */
    public void setMessage(String message) { this.message = message; }

    /**
     *
     * @return successfulness of person service
     */
    public boolean getSuccess() { return success; }

    /**
     *
     * @param success new successfulness of person service
     */
    public void setSuccess(boolean success) { this.success = success; }

    public Set<Person> getDataAsSet() {
        Set<Person> temp = new HashSet<Person>();
        for (Person p : data) {
            temp.add(p);
        }

        //Set<Person> temp = new HashSet<Person>(Arrays.asList(data));
        return temp;
    }
}
