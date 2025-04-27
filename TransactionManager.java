import java.util.*;
import java.util.logging.*;
import java.io.*;

public class TransactionManager {
    private static final Logger logger = Logger.getLogger(TransactionManager.class.getName());
    public static final Scanner sc = new Scanner(System.in);
    private final String pin  = "3715";
    private static final String TRANSACTION_FILE = "Transactions.txt";

    public void addTransaction(int numOfTransaction) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TRANSACTION_FILE, true))) {
            int[] transaction = new int[numOfTransaction];
            for (int i = 0; i < transaction.length; i++) {
                System.out.print("Enter Transaction (-ve or +ve sign for deposit or withdrawal, in INR): ");
                try {
                    transaction[i] = sc.nextInt();
                    sc.nextLine(); // consume leftover newline
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a valid number.");
                    sc.next(); // Consume invalid token
                    i--; // Stay on the same index
                }
            }
            for (int j : transaction) {
                writer.write(Integer.toString(j));
                writer.newLine();
            }
            System.out.println("Transactions written in the File.");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "An Error Occurred", e);
            System.out.println("Oops! Something went wrong. Please try again later.");
        }
    }

    public void checkTransaction() {
        try (BufferedReader reader = new BufferedReader(new FileReader(TRANSACTION_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "An Error Occurred", e);
            System.out.println("Oops! Something went wrong. Please try again later.");
        }
    }
    public static void main(String[] args) {
        TransactionManager manager = new TransactionManager();
        try {
            System.out.print("Enter Account Holder Name: ");
            String name = sc.nextLine();

            int attempts = 3;

            while (true) {
                System.out.print("Enter your 4-digit PIN: ");
                String PIN = sc.nextLine();
                    if (PIN.equals(manager.pin)) {
                        System.out.println("Welcome, " + name);
                        System.out.println("1. Add Transaction \n2. Check Transaction History \n3. Exit");
                        System.out.print("Enter Choice: ");
                        int choice;
                        try {
                            choice = sc.nextInt();
                            sc.nextLine(); // consume leftover newline
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input, please enter a number.");
                            sc.next(); // consume invalid token
                            continue;
                        }
                        switch (choice) {
                            case 1:
                                System.out.print("Enter number of transaction made: ");
                                int numOfTransactions = sc.nextInt();
                                sc.nextLine(); // consume leftover newline
                                manager.addTransaction(numOfTransactions);
                                break;
                            case 2:
                                System.out.println("Fetching your Transaction History");
                                manager.checkTransaction();
                                break;
                            case 3:
                                System.out.println("Thank you, Have a Good Day Ahead!");
                                System.exit(0);
                                break;
                            default:
                                System.out.println("Invalid Option, Please Try again");
                                break;
                        }
                    } else {
                        System.out.println("Incorrect PIN");
                        attempts--;
                        if (attempts == 0) {
                            System.out.println("Too many incorrect attempts, Access Denied");
                            System.exit(0);
                        }
                    }
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid Input");
        }
        sc.close();
    }
}
