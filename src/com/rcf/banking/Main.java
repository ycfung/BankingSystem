package com.rcf.banking;

import com.rcf.banking.controller.AccountController;
import com.rcf.banking.controller.TransactionController;
import com.rcf.banking.entity.Account;
import com.rcf.banking.exception.AccountAlreadyExistsException;
import com.rcf.banking.repository.AccountRepository;
import com.rcf.banking.util.Currency;
import com.rcf.banking.util.HostHolder;
import com.rcf.banking.util.Input;

public class Main {

    /**
     * A testing command line interface
     *
     * @param args unused arguments
     */
    public static void main(String[] args) throws AccountAlreadyExistsException {

        // pre-define account
        AccountRepository.addAccount(new Account("Alice", Currency.USD, 100));
        AccountRepository.addAccount(new Account("Bob", Currency.USD, 1000));
        AccountRepository.addAccount(new Account("Bob", Currency.HKD, 2000));
        HostHolder.setUserName("Bob");

        // the built-in account is added in the AccountRepository class, not here

        while (true) {
            System.out.println();
            System.out.println("1. Create a new account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. Check balance");
            System.out.println("6. Display statement");
            System.out.println("7. Exit");
            // For testing, I add an extra option to change to another user, to see if the transfer works
            System.out.println("8. Testing - Change user");
            int option = Input.InputInt(1, 8);
            switch (option) {
                case 1:
                    AccountController.openAccount();
                    break;
                case 2:
                    TransactionController.deposit();
                    break;
                case 3:
                    TransactionController.withdraw();
                    break;
                case 4:
                    TransactionController.transfer();
                    break;
                case 5:
                    AccountController.printBalance();
                    break;
                case 6:
                    AccountController.displayStatement();
                    break;
                case 7:
                    System.out.println("Goodbye");
                    return;
                case 8:
                    System.out.println("Enter a valid username:");
                    String userName = Input.inputString();
                    HostHolder.setUserName(userName);
                    System.out.println("The user is changed to " + userName);
                    break;
            }
        }

    }
}
