package Service;

import DataAccess.DataAccessException;
import DataAccess.Database;
import Result.ClearResult;

/**
 * This class clears all data from a database
 */
public class Clear {
    private Database database = new Database();
    //private Connection conn = database.openConnection();

    public Clear() throws DataAccessException {}

    /**
     * Begins the process of deleting user accounts, auth tokens, and person and event data
     *
     * @return The result of clearing all data
     */
    public ClearResult clear() {
        ClearResult result = new ClearResult();
        try {
            database.openConnection();
            database.clearTables();
            database.closeConnection(true);
            result.setMessage("Clear Succeeded.");
            result.setSuccess(true);
            return result;
        } catch(DataAccessException e) {
            result.setMessage("Error: Data Access Error");
            result.setSuccess(false);
            return result;
        }
    }
}
