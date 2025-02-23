package atm;

public class Security {
    private final Authenticator authenticator;

    /**
     * Constructor for Security class.
     * @param authenticator The Authenticator instance to handle credentials.
     */
    public Security(Authenticator authenticator) {
        this.authenticator = authenticator;
    }

    /**
     * Changes the user's password.
     * @param account The account for which the password needs to be changed.
     * @param oldPassword The current password.
     * @param newPassword The new password.
     * @return true if the password was successfully changed, false otherwise.
     */
    public boolean changePassword(String account, String oldPassword, String newPassword) {
        // Validate the old password
        if (!authenticator.authenticate(account, oldPassword)) {
            return false; // Old password incorrect
        }
        // New password must not be empty
        if (newPassword == null || newPassword.isEmpty()) {
            return false;
        }
        // New password must not be the same as the old password
        if (newPassword.equals(oldPassword)) {
            return false;
        }
        // Change password
        authenticator.addUser(account, newPassword); // Overwrites the old password
        return true;
    }
}
