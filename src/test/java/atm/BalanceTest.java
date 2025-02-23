package atm;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 * Unit tests for the Balance class.
 */
class BalanceTest {
    private Balance balance;
    private History mockHistory;

    /**
     * Initializes a fresh Balance instance with a mock history before each test.
     */
    @BeforeEach
    void setUp() {
        mockHistory = Mockito.mock(History.class); // 🔹 Mock history to verify interactions
        balance = new Balance(1000.0, mockHistory); // Starting balance: $1000
    }

    /**
     * Tests successful deposits.
     */
    @Test
    void testDepositSuccess() {
        assertTrue(balance.deposit(500.0), "Deposit should succeed.");
        assertEquals(1500.0, balance.getBalance(), 0.01, "Balance should increase after deposit.");
        Mockito.verify(mockHistory).recordTransaction("deposit", 500.0); // 🔹 Ensures history is logged
    }

    /**
     * Tests deposit of invalid amounts.
     */
    @Test
    void testDepositInvalidAmount() {
        assertFalse(balance.deposit(-100.0), "Negative deposit should fail.");
        assertFalse(balance.deposit(0.0), "Zero deposit should fail.");
        assertEquals(1000.0, balance.getBalance(), 0.01, "Balance should remain unchanged.");
        Mockito.verifyNoInteractions(mockHistory); // 🔹 Ensures history is NOT logged
    }

    /**
     * Tests successful withdrawals.
     */
    @Test
    void testWithdrawSuccess() {
        assertTrue(balance.withdraw(400.0), "Withdrawal should succeed.");
        assertEquals(600.0, balance.getBalance(), 0.01, "Balance should decrease after withdrawal.");
        Mockito.verify(mockHistory).recordTransaction("withdraw", 400.0); // 🔹 Ensures history is logged
    }

    /**
     * Tests withdrawals exceeding balance.
     */
    @Test
    void testWithdrawInsufficientFunds() {
        assertFalse(balance.withdraw(1200.0), "Should fail when withdrawing more than balance.");
        assertEquals(1000.0, balance.getBalance(), 0.01, "Balance should remain unchanged.");
        Mockito.verifyNoInteractions(mockHistory); // 🔹 No history entry if withdrawal fails
    }

    /**
     * Tests withdrawal of invalid amounts.
     */
    @Test
    void testWithdrawInvalidAmount() {
        assertFalse(balance.withdraw(-50.0), "Negative withdrawal should fail.");
        assertFalse(balance.withdraw(0.0), "Zero withdrawal should fail.");
        assertEquals(1000.0, balance.getBalance(), 0.01, "Balance should remain unchanged.");
        Mockito.verifyNoInteractions(mockHistory);
    }

    /**
     * Tests if history logging is skipped when no history instance is provided.
     */
    @Test
    void testBalanceWithoutHistory() {
        Balance balanceWithoutHistory = new Balance(500.0, null);
        assertTrue(balanceWithoutHistory.deposit(100.0), "Deposit should still work without history.");
        assertTrue(balanceWithoutHistory.withdraw(50.0), "Withdrawal should still work without history.");
        assertEquals(550.0, balanceWithoutHistory.getBalance(), 0.01, "Balance should update correctly.");
    }
}
