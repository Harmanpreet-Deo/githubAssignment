package atm;

/**
 * The Balance class manages an account's balance with deposit and withdrawal functionality.
 * It logs transactions if a History instance is provided.
 */
public class Balance {
    private double balance;
    private final History history; // Transaction history tracker (optional)

    /**
     * Constructor initializes balance with an optional history tracker.
     * @param initialBalance The starting balance.
     * @param history The history instance to track transactions (can be null).
     */
    public Balance(double initialBalance, History history) {
        this.balance = initialBalance;
        this.history = history;
    }

    /**
     * Retrieves the current balance.
     * @return The account balance.
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Deposits a valid amount into the account and records it in history.
     * @param amount The deposit amount.
     * @return true if deposit is successful, false otherwise.
     */
    public boolean deposit(double amount) {
        if (amount <= 0) {
            return false; // Invalid deposit amount
        }
        balance += amount;
        if (history != null) {
            history.recordTransaction("deposit", amount); // 🔹 Uses correct method
        }
        return true;
    }

    /**
     * Withdraws a valid amount from the account and records it in history.
     * @param amount The withdrawal amount.
     * @return true if withdrawal is successful, false otherwise.
     */
    public boolean withdraw(double amount) {
        if (amount <= 0 || amount > balance) {
            return false; // Invalid withdrawal
        }
        balance -= amount;
        if (history != null) {
            history.recordTransaction("withdraw", amount); // 🔹 Uses correct method
        }
        return true;
    }
}
