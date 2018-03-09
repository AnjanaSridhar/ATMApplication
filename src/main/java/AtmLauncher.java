import com.atm.dao.AccountDaoImpl;
import com.atm.dao.AtmDaoImpl;
import com.atm.service.account.AccountService;
import com.atm.service.account.AccountServiceImpl;
import com.atm.service.atm.AtmService;
import com.atm.service.atm.AtmServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Scanner;

public class AtmLauncher {
    private static final Logger LOGGER = LogManager.getLogger("AtmLauncher");
    static AccountService accountService = new AccountServiceImpl();
    static AtmService atmService = new AtmServiceImpl();
    static Scanner inp = new Scanner(System.in);

    public static void main(String args[]) {
        LOGGER.info("Starting ATM application");
        welcomeScreen();
    }

    //Method to display screen that takes account number as input.
    // Also checks if the account number is valid
    private static void welcomeScreen() {
        LOGGER.info("Displaying main menu");
        System.out.println("Welcome! Please Enter Your Account Number!");
        String accountNumber = inp.next();

        if (accountNumber.isEmpty() || !accountService.accountExists(accountNumber)) {
            System.out.println("Please enter a valid account number!");
            welcomeScreen();
        } else {
            menu(accountNumber);
        }
    }

    //Method that displays operations for a valid account number.
    private static void menu(String accountNumber) {
        LOGGER.info("Displaying operation available for account: {}", accountNumber);
        if ("Adm1n".equals(accountNumber)) {
            System.out.println("0. ATM Note Replenish");
        } else {
            System.out.println("\n1. Check Balance \n2. Withdraw \n3. Exit");
        }
        int option = inp.nextInt();
        switch (option) {
            case 1:
                NumberFormat format = NumberFormat.getCurrencyInstance(Locale.UK);
                System.out.println(new StringBuffer("Your balance is ")
                        .append(format.format(accountService.checkBalance(accountNumber))));
                menu(accountNumber);
                break;
            case 2:
                System.out.println("Enter amount (NOTE: Withdrawals allowed between £5 and £250(inclusive) " +
                        "in multiples of 5)");
                int amount = inp.nextInt();
                if (amount < 5 || amount > 250) {
                    System.out.println("Withdrawals allowed between £5 and £250(inclusive)");
                    menu(accountNumber);
                } else {
                    System.out.println(atmService.withdraw(amount, accountNumber));
                    menu(accountNumber);
                }

                break;
            case 3:
                LOGGER.info("Exiting application.");
                System.out.println("Thank you!");
                System.exit(1);
            case 0:
                LOGGER.debug("Entering admin screen");
                //setting up ATM with money
                new AtmDaoImpl().replenish(35);
                System.out.println("ATM notes successfully replenished!");
                System.exit(1);

            default:
                menu(accountNumber);
        }
    }
}
