package atm;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * Unit tests for the History class.
 */
class HistoryTest {
    private History history;

    /**
     * Initializes a fresh History instance before each test.
     */
    @BeforeEach
    void setUp() {
        history = new History();
    }

    /**
     * Tests if deposit transactions are recorded correctly.
     */
    @Test
    void testRecordDeposit() {
        history.recordTransaction("deposit", 500.0);
        List<String> transactions = history.getHistory();

        assertEquals(1, transactions.size(), "Transaction history should contain one deposit.");
        assertTrue(transactions.get(0).contains("Deposited: $500.0"), "Transaction should indicate deposit.");
    }

    /**
     * Tests if withdrawal transactions are recorded correctly.
     */
    @Test
    void testRecordWithdrawal() {
        history.recordTransaction("withdraw", 200.0);
        List<String> transactions = history.getHistory();

        assertEquals(1, transactions.size(), "Transaction history should contain one withdrawal.");
        assertTrue(transactions.get(0).contains("Withdrawn: $200.0"), "Transaction should indicate withdrawal.");
    }

    /**
     * Tests filtering of deposit transactions.
     */
    @Test
    void testGetFilteredHistoryForDeposits() {
        history.recordTransaction("deposit", 100.0);
        history.recordTransaction("withdraw", 50.0);
        history.recordTransaction("deposit", 200.0);

        List<String> deposits = history.getFilteredHistory("deposit");

        assertEquals(2, deposits.size(), "Should return only deposit transactions.");
        assertTrue(deposits.get(0).contains("Deposited: $100.0"));
        assertTrue(deposits.get(1).contains("Deposited: $200.0"));
    }

    /**
     * Tests filtering of withdrawal transactions.
     */
    @Test
    void testGetFilteredHistoryForWithdrawals() {
        history.recordTransaction("deposit", 300.0);
        history.recordTransaction("withdraw", 75.0);
        history.recordTransaction("withdraw", 25.0);

        List<String> withdrawals = history.getFilteredHistory("withdraw");

        assertEquals(2, withdrawals.size(), "Should return only withdrawal transactions.");
        assertTrue(withdrawals.get(0).contains("Withdrawn: $75.0"));
        assertTrue(withdrawals.get(1).contains("Withdrawn: $25.0"));
    }

    /**
     * Tests formatted history retrieval.
     */
    @Test
    void testGetFormattedHistory() {
        history.recordTransaction("deposit", 150.0);
        history.recordTransaction("withdraw", 50.0);

        String formattedHistory = history.getFormattedHistory();

        assertTrue(formattedHistory.contains("Deposited: $150.0"));
        assertTrue(formattedHistory.contains("Withdrawn: $50.0"));
    }

    /**
     * Tests formatted history when there are no transactions.
     */
    @Test
    void testEmptyHistoryMessage() {
        assertEquals("No transaction history available.", history.getFormattedHistory(),
                "Empty history should return appropriate message.");
    }

    /**
     * Tests that an invalid transaction type throws an exception.
     */
    @Test
    void testInvalidTransactionType() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            history.recordTransaction("invalidType", 100.0);
        });

        assertTrue(exception.getMessage().contains("Invalid transaction type"),
                "Exception message should indicate invalid type.");
    }
}
