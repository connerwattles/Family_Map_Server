package Request;

/**
 * The username and number of generations needed for fill service
 */
public class FillRequest {
    /**
     * The username to populate fill generations
     */
    String username = null;
    /**
     * The number of generations to fill
     */
    int generations = 0;

    /**
     *
     * @return The fill request username
     */
    public String getUsername() { return username; }

    /**
     *
     * @param username The new fill request username
     */
    public void setUsername(String username) { this.username = username; }

    /**
     *
     * @return The fill request generations
     */
    public int getGenerations() { return generations; }

    /**
     *
     * @param generations The new fill request number of generations
     */
    public void setGenerations(int generations) { this.generations = generations; }
}
