package Result;

/**
 * The contents for the response of load service
 */
public class LoadResult {
    /**
     * The message response
     */
    String message = null;
    /**
     * Whether the service was successful or not
     */
    boolean success;

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
