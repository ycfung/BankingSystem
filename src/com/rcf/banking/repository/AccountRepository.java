package com.rcf.banking.repository;

import com.rcf.banking.entity.Account;
import com.rcf.banking.exception.AccountAlreadyExistsException;
import com.rcf.banking.exception.NotEnoughBalanceException;
import com.rcf.banking.exception.UserNotFoundException;
import com.rcf.banking.util.Currency;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contains the methods to access the database.
 * But in this case, the database is not implemented, so the methods simulate the database access.
 *
 * @author Chengfeng RONG
 * @version 1.0
 */
public class AccountRepository {

    /**
     * This list contains the accounts of the users.
     * It actually simulates the database table.
     * I know it may be more efficient to use a map or set, but I want to keep the code simple and easy to understand.
     */
    private static List<Account> accounts = new ArrayList<>();

    // getter
    public static List<Account> getAccounts() {
        return accounts;
    }

    // add the built-in account
    static {
        accounts.add(new Account("OSL_FEE", Currency.USD, 0.0));
        accounts.add(new Account("OSL_FEE", Currency.HKD, 0.0));
        accounts.add(new Account("OSL_FEE", Currency.SGD, 0.0));
    }

    /**
     * Get the account by username
     *
     * @param userName username of the account
     * @throws UserNotFoundException if the account already exists
     */
    public static Account getAccountByUserNameAndCurrency(String userName, Currency currency) throws UserNotFoundException {
        for (Account account : accounts) {
            if (account.getUserName().equals(userName) && account.getCurrency().equals(currency)) {
                return account;
            }
        }
        throw new UserNotFoundException();
    }


    /**
     * This method simulates the database access to add a new account to the list
     *
     * @param account account to add
     * @return 0 if the account is added
     * @throws AccountAlreadyExistsException if the account already exists
     */
    public static int addAccount(Account account) throws AccountAlreadyExistsException {

        // check if the username already exists
        boolean exist = false;
        for (Account a : accounts) {
            if (a.getUserName().equals(account.getUserName()) && a.getCurrency().equals(account.getCurrency())) {
                exist = true;
                break;
            }
        }

        // if the account does not exist, add the account to the list
        if (!exist) {
            accounts.add(account);
            return 0;
        } else {
            throw new AccountAlreadyExistsException();
        }
    }


    /**
     * This method simulates the database access to get the available balance of a user.
     *
     * @param userName the username
     * @return the available balance, or -1 if the user does not exist
     * @throws UserNotFoundException if the user does not exist
     */
    public static double getBalanceByUserNameAndCurrency(String userName, Currency currency) throws UserNotFoundException {
        for (Account account : accounts) {
            if (account.getUserName().equals(userName) && account.getCurrency() == currency) {
                return account.getBalance();
            }
        }
        throw new UserNotFoundException();
    }


    /**
     * This method simulates the database access to add money to balance
     *
     * @param userName the client username
     * @param amount   the amount to deposit
     * @return 0 if the deposit is successful, -1 if the deposit is not successful
     * @throws UserNotFoundException if the user does not exist
     */
    public static int addBalanceByUserNameAndCurrency(String userName, double amount, Currency currency) throws UserNotFoundException {
        for (Account account : accounts) {
            if (account.getUserName().equals(userName) && account.getCurrency() == currency) {
                account.setBalance(account.getBalance() + amount);
                return 0;
            }
        }
        throw new UserNotFoundException();
    }


    /**
     * This method simulates the database access to subtract money.
     *
     * @param userName the client username
     * @param amount   the amount to withdraw
     * @return 0 if the transaction is successful
     * @throws UserNotFoundException     if the user does not exist
     * @throws NotEnoughBalanceException if the user does not have enough balance
     */
    public static int subtractBalanceByUserNameAndCurrency(String userName, double amount, Currency currency) throws NotEnoughBalanceException, UserNotFoundException {
        for (Account account : accounts) {
            if (account.getUserName().equals(userName) && account.getCurrency() == currency) {
                if (account.getBalance() >= amount) {
                    account.setBalance(account.getBalance() - amount);
                    return 0;
                } else {
                    throw new NotEnoughBalanceException();
                }
            }
        }
        throw new UserNotFoundException();
    }

    /**
     * This method simulates the database access to get all accounts of a user
     *
     * @param userName the username
     * @return the list of accounts
     */
    public static List<Account> getAllAccountsByUserName(String userName) throws UserNotFoundException {
        List<Account> accounts = new ArrayList<>();
        for (Account account : AccountRepository.accounts) {
            if (account.getUserName().equals(userName)) {
                accounts.add(account);
            }
        }
        if (accounts.size() == 0) {
            throw new UserNotFoundException();
        } else {
            return accounts;
        }
    }


}
