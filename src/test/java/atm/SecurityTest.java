package atm;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * JUnit test cases for the Security class.
 * Tests password changes and validation.
 */
class SecurityTest {
    private Security security;
    private Authenticator authenticator;

    /**
     * Sets up test environment with a fresh Authenticator and Security instance.
     */
    @BeforeEach
    void setUp() {
        authenticator = new Authenticator();
        security = new Security(authenticator);
    }

    /**
     * Tests successful password change.
     */
    @Test
    void testSuccessfulPasswordChange() {
        assertTrue(security.changePassword("1234567", "abcdef", "newpass"));
        assertTrue(authenticator.authenticate("1234567", "newpass"));
    }

    /**
     * Tests changing password with incorrect old password.
     */
    @Test
    void testChangePasswordWithIncorrectOldPassword() {
        assertFalse(security.changePassword("1234567", "wrongpass", "newpass"));
    }

    /**
     * Tests changing password to an empty value.
     */
    @Test
    void testChangePasswordToEmpty() {
        assertFalse(security.changePassword("1234567", "abcdef", ""));
    }

    /**
     * Tests changing password to the same as the old password.
     */
    @Test
    void testChangePasswordToSameValue() {
        assertFalse(security.changePassword("1234567", "abcdef", "abcdef"));
    }
}
