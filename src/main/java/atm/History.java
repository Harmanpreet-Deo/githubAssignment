package atm;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The History class tracks and stores account transactions with timestamps.
 * Provides filtering and formatted display of transaction history.
 */
public class History {
    private final List<String> history; // Stores transaction history
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Constructor initializes history tracking.
     */
    public History() {
        this.history = new ArrayList<>();
    }

    /**
     * Records a transaction in history with a timestamp.
     * @param type The type of transaction ("deposit" or "withdraw").
     * @param amount The amount involved in the transaction.
     */
    public void recordTransaction(String type, double amount) {
        String timestamp = LocalDateTime.now().format(FORMATTER);
        String entry = "[" + timestamp + "] ";

        switch (type.toLowerCase()) {
            case "deposit" -> history.add(entry + "Deposited: $" + amount);
            case "withdraw" -> history.add(entry + "Withdrawn: $" + amount);
            default -> throw new IllegalArgumentException("Invalid transaction type: " + type);
        }
    }

    /**
     * Retrieves all account transactions.
     * @return A copy of the transaction history list.
     */
    public List<String> getHistory() {
        return new ArrayList<>(history); // Return a copy to avoid external modifications
    }

    /**
     * Retrieves transactions of a specific type ("deposit" or "withdraw").
     * @param type The transaction type to filter by.
     * @return A filtered list of transactions.
     */
    public List<String> getFilteredHistory(String type) {
        return history.stream()
                .filter(entry -> entry.contains(type.equalsIgnoreCase("deposit") ? "Deposited" : "Withdrawn"))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a formatted account summary.
     * @return A formatted string containing all transactions or a message if no transactions exist.
     */
    public String getFormattedHistory() {
        return history.isEmpty() ? "No transaction history available." : String.join("\n", history);
    }
}
