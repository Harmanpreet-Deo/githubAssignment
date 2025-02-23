package atm;

import java.util.Scanner;

/**
 * The ATM_Machine class serves as the main entry point for ATM operations.
 * It manages authentication, balance transactions, security features, and account history.
 */
public class ATM_Machine {
    private final Authenticator authenticator;
    private final Balance balance;
    private final Security security;
    private final History history;
    private final Scanner keyboard;

    /**
     * Constructor initializes ATM system with dependencies.
     */
    public ATM_Machine(Authenticator authenticator, Balance balance, Security security, History history, Scanner scanner) {
        this.authenticator = authenticator;
        this.balance = balance;
        this.security = security;
        this.history = history;
        this.keyboard = scanner; // Injected scanner (mockable in tests)
    }

    /**
     * Runs the ATM system with a menu-driven interface.
     */
    public void start() {
        String account;
        String password;

        while (authenticator.hasAttemptsLeft()) { // 🔹 Retry until attempts are exhausted
            System.out.print("Enter account number: ");
            account = keyboard.next();
            System.out.print("Enter password: ");
            password = keyboard.next();

            if (authenticator.authenticate(account, password)) {
                System.out.println("Authentication successful!\n");
                runATMOperations(account); // 🔹 Proceed with ATM operations
                return; // Exit authentication loop upon success
            } else {
                System.out.println("Authentication failed. Attempts remaining: " + authenticator.getAttemptsLeft());
            }
        }

        System.out.println("Too many failed attempts. Exiting system."); // When attempts are exhausted
    }

    /**
     * Runs the ATM operations after successful authentication.
     */
    private void runATMOperations(String account) {
        int option;
        do {
            displayMenu();
            option = getUserInput();
            processOption(option, account);
        } while (option != 6); // Exit when the user selects 6
    }

    /**
     * Displays the ATM menu options.
     */
    private void displayMenu() {
        System.out.println("\nWhat service would you like?");
        System.out.println("1. Deposit");
        System.out.println("2. Withdraw");
        System.out.println("3. Print Balance");
        System.out.println("4. Print Activities");
        System.out.println("5. Change Password");
        System.out.println("6. Exit");
        System.out.print("Enter your option: ");
    }

    /**
     * Gets user input for menu selection.
     * @return The selected menu option.
     */
    private int getUserInput() {
        try {
            return keyboard.nextInt();
        } catch (Exception e) {
            keyboard.next(); // Clear invalid input
            return -1; // Return an invalid option to handle error
        }
    }

    /**
     * Processes user-selected menu option.
     * @param option The menu option chosen.
     * @param account The authenticated user's account number.
     */
    protected void processOption(int option, String account) {
        switch (option) {
            case 1 -> handleDeposit();
            case 2 -> handleWithdraw();
            case 3 -> System.out.println("Current Balance: $" + balance.getBalance());
            case 4 -> System.out.println(history.getFormattedHistory()); // 🔹 Ensure this prints history
            case 5 -> handlePasswordChange(account);
            case 6 -> System.out.println("Thank you for using ATM Machine.");
            default -> System.out.println("Invalid option. Try again.");
        }
    }

    /**
     * Handles deposit operation and logs transaction.
     */
    protected void handleDeposit() {
        System.out.print("Enter deposit amount: ");
        double amount = keyboard.nextDouble();
        if (balance.deposit(amount)) {
            System.out.println("Deposit successful!");
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    /**
     * Handles withdrawal operation and logs transaction.
     */
    protected void handleWithdraw() {
        System.out.print("Enter withdrawal amount: ");
        double amount = keyboard.nextDouble();
        if (balance.withdraw(amount)) {
            System.out.println("Withdrawal successful!");
        } else {
            System.out.println("Invalid withdrawal amount or insufficient funds.");
        }
    }

    /**
     * Handles password change operation.
     * @param account The authenticated user's account number.
     */
    protected void handlePasswordChange(String account) {
        System.out.print("Enter current password: ");
        String oldPassword = keyboard.next();
        System.out.print("Enter new password: ");
        String newPassword = keyboard.next();

        if (security.changePassword(account, oldPassword, newPassword)) {
            System.out.println("Password changed successfully!");
        } else {
            System.out.println("Password change failed.");
        }
    }

    /**
     * **Main method to start the ATM system.**
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Authenticator authenticator = new Authenticator();
        History history = new History();
        Balance balance = new Balance(2000.0, history);
        Security security = new Security(authenticator);

        ATM_Machine atm = new ATM_Machine(authenticator, balance, security, history, scanner);
        atm.start();

        scanner.close(); // Close scanner when the application ends
    }
}
