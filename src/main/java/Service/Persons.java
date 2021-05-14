package Service;

import DataAccess.AuthTokenDao;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.PersonDao;
import Model.AuthToken;
import Model.Person;
import Request.PersonsRequest;
import Result.PersonsResult;

import java.sql.Connection;
import java.util.ArrayList;

/**
 * A class to return person objects and their ID's
 */
public class Persons {
    private Database database = new Database();
    private PersonDao personDao;
    private AuthTokenDao authTokenDao;

    public Persons() throws DataAccessException {
    }

    /**
     * The beginning of the person service
     *
     * @param r The request values are either an authToken or person ID
     * @return The result person objects or data attached to a single person
     */
    public PersonsResult persons(PersonsRequest r) throws DataAccessException {
        Connection conn = database.openConnection();
        personDao = new PersonDao(conn);
        authTokenDao = new AuthTokenDao(conn);

        String token = r.getAuthToken();
        String personID = r.getPersonID();
        PersonsResult result = new PersonsResult();

        if (personID != null) {
            try {
                Person foundPerson = personDao.find(personID);
                AuthToken foundToken = authTokenDao.findFromToken(token);
                if (foundPerson == null) {
                    result.setMessage("Error: Person not found");
                    result.setSuccess(false);
                    database.closeConnection(false);
                }
                else if (!foundPerson.getUsername().equals(foundToken.getUsername())) {
                    result.setMessage("Error: Given PersonID not associated with token");
                    result.setSuccess(false);
                    database.closeConnection(false);
                }
                else {
                    result.setUsername(foundPerson.getUsername());
                    result.setPersonID(foundPerson.getPersonID());
                    result.setFirstName(foundPerson.getFirstName());
                    result.setLastName(foundPerson.getLastName());
                    result.setGender(foundPerson.getGender());
                    result.setFatherID(foundPerson.getFatherID());
                    result.setMotherID(foundPerson.getMotherID());
                    result.setSpouseID(foundPerson.getSpouseID());
                    result.setSuccess(true);
                    database.closeConnection(true);
                }
                return result;
            } catch (DataAccessException e) {
                result.setMessage("Error: Data Access Error");
                result.setSuccess(false);
                database.closeConnection(false);
                return result;
            }
        }
        else {
            try {
                AuthToken authToken = authTokenDao.findFromToken(token);
                if (authToken == null) {
                    result.setMessage("Error: Invalid Auth Token");
                    result.setSuccess(false);
                    database.closeConnection(false);
                    return result;
                }
                else {
                    ArrayList<Person> allPersons = personDao.allPersons(authToken.getUsername());
                    if (allPersons.size() <= 0) {
                        result.setMessage("Error: No Persons");
                        result.setSuccess(false);
                        database.closeConnection(false);
                        return result;
                    }
                    else {
                        //How to convert an ArrayList to Array
                        Person[] people = allPersons.toArray(new Person[0]);
                        result.setData(people);
                        result.setSuccess(true);
                        database.closeConnection(true);
                        return result;
                    }
                }
            } catch (DataAccessException e) {
                result.setMessage("Error: Data Access Error");
                result.setSuccess(false);
                database.closeConnection(false);
                return result;
            }
        }
    }
}
