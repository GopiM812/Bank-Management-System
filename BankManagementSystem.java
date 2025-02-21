import java.util.*;
import java.io.*;

class BankAccount implements Serializable {
    private String accountNumber;
    private String holderName;
    private double balance;

    public BankAccount(String accountNumber, String holderName, double balance) {
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.balance = balance;
    }

    public String getAccountNumber() { return accountNumber; }
    public String getHolderName() { return holderName; }
    public double getBalance() { return balance; }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited: " + amount);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Withdrawn: " + amount);
        } else {
            System.out.println("Invalid or insufficient funds.");
        }
    }

    @Override
    public String toString() {
        return "Account No: " + accountNumber + ", Holder: " + holderName + ", Balance: $" + balance;
    }
}

public class BankManagementSystem {
    private static final String FILE_NAME = "accounts.dat";
    private static HashMap<String, BankAccount> accounts = new HashMap<>();

    public static void main(String[] args) {
        loadAccounts();
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- Bank Management System ---");
            System.out.println("1. Create Account");
            System.out.println("2. View Account");
            System.out.println("3. Deposit");
            System.out.println("4. Withdraw");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    createAccount(sc);
                    break;
                case 2:
                    viewAccount(sc);
                    break;
                case 3:
                    deposit(sc);
                    break;
                case 4:
                    withdraw(sc);
                    break;
                case 5:
                    saveAccounts();
                    System.out.println("Exiting... Goodbye!");
                    sc.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void createAccount(Scanner sc) {
        System.out.print("Enter Account Number: ");
        String accNo = sc.next();
        System.out.print("Enter Holder Name: ");
        sc.nextLine(); // Consume newline
        String name = sc.nextLine();
        System.out.print("Enter Initial Balance: ");
        double balance = sc.nextDouble();

        if (accounts.containsKey(accNo)) {
            System.out.println("Account already exists.");
        } else {
            accounts.put(accNo, new BankAccount(accNo, name, balance));
            System.out.println("Account created successfully.");
        }
    }

    private static void viewAccount(Scanner sc) {
        System.out.print("Enter Account Number: ");
        String accNo = sc.next();
        BankAccount account = accounts.get(accNo);
        if (account != null) {
            System.out.println(account);
        } else {
            System.out.println("Account not found.");
        }
    }

    private static void deposit(Scanner sc) {
        System.out.print("Enter Account Number: ");
        String accNo = sc.next();
        BankAccount account = accounts.get(accNo);
        if (account != null) {
            System.out.print("Enter Amount to Deposit: ");
            double amount = sc.nextDouble();
            account.deposit(amount);
        } else {
            System.out.println("Account not found.");
        }
    }

    private static void withdraw(Scanner sc) {
        System.out.print("Enter Account Number: ");
        String accNo = sc.next();
        BankAccount account = accounts.get(accNo);
        if (account != null) {
            System.out.print("Enter Amount to Withdraw: ");
            double amount = sc.nextDouble();
            account.withdraw(amount);
        } else {
            System.out.println("Account not found.");
        }
    }

    private static void loadAccounts() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            accounts = (HashMap<String, BankAccount>) ois.readObject();
        } catch (Exception e) {
            accounts = new HashMap<>();
        }
    }

    private static void saveAccounts() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(accounts);
        } catch (IOException e) {
            System.out.println("Error saving accounts.");
        }
    }
}