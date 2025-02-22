package atm;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * JUnit test cases for the Authenticator class.
 * Tests authentication logic, user addition, and login attempt limits.
 */
class AuthenticatorTest {
    private Authenticator authenticator;

    /**
     * Sets up a fresh Authenticator instance before each test.
     */
    @BeforeEach
    void setUp() {
        authenticator = new Authenticator();
    }

    /**
     * Tests successful authentication with default credentials.
     */
    @Test
    void testSuccessfulAuthentication() {
        assertTrue(authenticator.authenticate("1234567", "abcdef"));
    }

    /**
     * Tests authentication failure with incorrect credentials.
     */
    @Test
    void testFailedAuthentication() {
        assertFalse(authenticator.authenticate("1234567", "wrongpass"));
        assertTrue(authenticator.hasAttemptsLeft());
    }

    /**
     * Tests adding a new user and verifying successful authentication.
     */
    @Test
    void testAddUserAndAuthenticate() {
        authenticator.addUser("user2", "pass2");
        assertTrue(authenticator.authenticate("user2", "pass2"));
    }

    /**
     * Tests authentication failure after exceeding login attempt limits.
     * Also tests the method for returning left login attempts.
     */
    @Test
    void testFailedAttemptsLimit() {
        authenticator.authenticate("1234567", "wrong");
        authenticator.authenticate("1234567", "wrong");
        authenticator.authenticate("1234567", "wrong");
        assertFalse(authenticator.hasAttemptsLeft());
        assertEquals(0, authenticator.getAttemptsLeft());
    }
}
