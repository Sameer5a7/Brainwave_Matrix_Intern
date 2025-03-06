package atm;

import bank.Account;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ATM implements ATMInterface {
    private Account account;
    private List<String> transactionHistory;
    private int pin;

    public ATM(Account account, int pin) {
        this.account = account;
        this.transactionHistory = new ArrayList<>();
        this.pin = pin;
    }

    private boolean authenticate(Scanner scanner) {
        System.out.print("Enter your 4-digit PIN: ");
        int enteredPin = scanner.nextInt();
        return enteredPin == pin;
    }

    @Override
    public void checkBalance() {
        System.out.println("\n🔹 Current Balance: ₹" + account.getBalance());
    }

    @Override
    public void deposit(double amount) {
        account.deposit(amount);
        transactionHistory.add("Deposited: ₹" + amount);
    }

    @Override
    public void withdraw(double amount) {
        if (account.withdraw(amount)) {
            transactionHistory.add("Withdrawn: ₹" + amount);
        }
    }

    public void showTransactionHistory() {
        System.out.println("\n Transaction History:");
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions yet.");
        } else {
            for (String transaction : transactionHistory) {
                System.out.println(transaction);
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Account userAccount = new Account(5000); // Initial balance
        ATM atm = new ATM(userAccount, 1234); // Default PIN

        // Authentication
        System.out.println("Welcome to the ATM!");
        if (!atm.authenticate(scanner)) {
            System.out.println(" Incorrect PIN. Exiting...");
            scanner.close();
            return;
        }

        // ATM Menu
        while (true) {
            System.out.println("\nATM Menu:");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transaction History");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    atm.checkBalance();
                    break;
                case 2:
                    System.out.print("Enter deposit amount: ₹");
                    double depositAmount = scanner.nextDouble();
                    atm.deposit(depositAmount);
                    break;
                case 3:
                    System.out.print("Enter withdrawal amount: ₹");
                    double withdrawAmount = scanner.nextDouble();
                    atm.withdraw(withdrawAmount);
                    break;
                case 4:
                    atm.showTransactionHistory();
                    break;
                case 5:
                    System.out.println("Thank you for using the ATM. Have a nice day! ");
                    scanner.close();
                    return;
                default:
                    System.out.println(" Invalid choice! Please try again.");
            }
        }
    }
}
