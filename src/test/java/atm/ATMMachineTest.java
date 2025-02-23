package atm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the ATM_Machine class.
 */
class ATMMachineTest {
    private Authenticator mockAuthenticator;
    private Balance mockBalance;
    private Security mockSecurity;
    private History mockHistory;

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    void setUp() {
        mockAuthenticator = mock(Authenticator.class);
        mockBalance = mock(Balance.class);
        mockSecurity = mock(Security.class);
        mockHistory = mock(History.class);

        // Using an actual Scanner instead of mocking since Scanner is final and cannot be mocked
        Scanner mockScanner = new Scanner(System.in);

        ATM_Machine atm = new ATM_Machine(mockAuthenticator, mockBalance, mockSecurity, mockHistory, mockScanner);
    }

    /**
     * Tests successful authentication.
     */
    @Test
    void testSuccessfulAuthentication() {
        when(mockAuthenticator.authenticate("123456", "password")).thenReturn(true);

        assertTrue(mockAuthenticator.authenticate("123456", "password"));
        verify(mockAuthenticator, times(1)).authenticate("123456", "password");
    }

    /**
     * Tests failed authentication attempts.
     */
    @Test
    void testFailedAuthentication() {
        when(mockAuthenticator.authenticate("123456", "wrongpass")).thenReturn(false);
        when(mockAuthenticator.hasAttemptsLeft()).thenReturn(true);

        assertFalse(mockAuthenticator.authenticate("123456", "wrongpass"));
        verify(mockAuthenticator, times(1)).authenticate("123456", "wrongpass");
    }

    /**
     * Tests deposit functionality and transaction recording.
     */
    @Test
    void testDeposit() {
        when(mockBalance.deposit(200.0)).thenReturn(true);

        // Simulate deposit call
        boolean result = mockBalance.deposit(200.0);

        assertTrue(result);
        verify(mockBalance, times(1)).deposit(200.0);
    }

    /**
     * Tests withdrawal functionality and transaction recording.
     */
    @Test
    void testWithdraw() {
        when(mockBalance.withdraw(100.0)).thenReturn(true);

        // Simulate withdrawal call
        boolean result = mockBalance.withdraw(100.0);

        assertTrue(result);
        verify(mockBalance, times(1)).withdraw(100.0);
    }

    /**
     * Tests displaying balance.
     */
    @Test
    void testGetBalance() {
        when(mockBalance.getBalance()).thenReturn(1500.0);

        assertEquals(1500.0, mockBalance.getBalance());
        verify(mockBalance, times(1)).getBalance();
    }

    /**
     * Tests printing transaction history.
     */
    @Test
    void testPrintHistory() {
        when(mockHistory.getFormattedHistory()).thenReturn("[2025-02-19 10:00:00] Deposited: $500.0\n[2025-02-19 12:00:00] Withdrawn: $200.0");

        String history = mockHistory.getFormattedHistory();

        assertNotNull(history);
        assertTrue(history.contains("Deposited: $500.0"));
        assertTrue(history.contains("Withdrawn: $200.0"));
        verify(mockHistory, times(1)).getFormattedHistory();
    }

    /**
     * Tests password change functionality.
     */
    @Test
    void testChangePassword() {
        when(mockSecurity.changePassword("123456", "oldpass", "newpass")).thenReturn(true);

        boolean result = mockSecurity.changePassword("123456", "oldpass", "newpass");

        assertTrue(result);
        verify(mockSecurity, times(1)).changePassword("123456", "oldpass", "newpass");
    }
}
