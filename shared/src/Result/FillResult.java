package Result;

/**
 * The contents of the result of fill service
 */
public class FillResult {
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
