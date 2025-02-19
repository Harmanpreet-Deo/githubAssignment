package atm;

import java.util.HashMap;
import java.util.Map;

/**
 * The Authenticator class handles user authentication by storing credentials
 * and validating login attempts with a limited number of retries.
 */
public class Authenticator {
    private final Map<String, String> userCredentials; // Stores user credentials
    private int attemptsLeft; // Number of remaining login attempts

    /**
     * Constructor initializes the authenticator with a default user.
     */
    public Authenticator() {
        userCredentials = new HashMap<>();
        userCredentials.put("1234567", "abcdef"); // Default user
        attemptsLeft = 3;
    }

    /**
     * Adds a new user with a specified account number and password.
     * @param account The account number of the user.
     * @param password The password of the user.
     */
    public void addUser(String account, String password) {
        userCredentials.put(account, password);
    }

    /**
     * Authenticates a user based on the provided credentials.
     * @param account The account number entered by the user.
     * @param password The password entered by the user.
     * @return true if authentication is successful, false otherwise.
     */
    public boolean authenticate(String account, String password) {
        if (userCredentials.containsKey(account) && userCredentials.get(account).equals(password)) {
            attemptsLeft = 3; // Reset attempts on success
            return true;
        } else {
            attemptsLeft--;
            return false;
        }
    }

    /**
     * Checks if the user has remaining login attempts.
     * @return true if there are attempts left, false otherwise.
     */
    public boolean hasAttemptsLeft() {
        return attemptsLeft > 0;
    }
}
