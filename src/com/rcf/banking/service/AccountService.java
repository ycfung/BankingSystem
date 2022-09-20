package com.rcf.banking.service;

import com.rcf.banking.entity.Account;
import com.rcf.banking.entity.Transaction;
import com.rcf.banking.exception.AccountAlreadyExistsException;
import com.rcf.banking.exception.UserNotFoundException;
import com.rcf.banking.repository.AccountRepository;
import com.rcf.banking.repository.TransactionRepository;
import com.rcf.banking.util.Currency;
import com.rcf.banking.util.HostHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * This class provides services for account management
 *
 * @author Chengfeng RONG
 * @version 1.0
 */
public class AccountService {

    /**
     * Create a new account
     *
     * @param userName username of the account
     * @param currency currency of the account
     * @return -1 if the account already exists, 0 if the account is created successfully
     */
    public static int openAccount(String userName, Currency currency) {
        // create a new account
        Account account = new Account(userName, currency, 0);
        // try to add the new account to the list of accounts
        try {
            AccountRepository.addAccount(account);
            HostHolder.setUserName(userName);
            return 0;
        } catch (AccountAlreadyExistsException e) {
            return -1;
        }

    }

    /**
     * Get the list of transactions by username
     *
     * @param userName user to get
     * @return the list of transactions, or null if the account does not exist
     */
    public static List<Transaction> getStatement(String userName) {
        try {
            return TransactionRepository.getStatementByUserName(userName);
        } catch (UserNotFoundException e) {
            return null;
        }
    }

    /**
     * Get the account in the specific currency
     *
     * @param userName username of the account
     * @param currency currency of the account
     * @return the account, or null if the account does not exist
     */
    public static Account getAccountByUserNameAndCurrency(String userName, Currency currency) {
        try {
            return AccountRepository.getAccountByUserNameAndCurrency(userName, currency);
        } catch (UserNotFoundException e) {
            return null;
        }
    }

    /**
     * Get the list of accounts by username
     *
     * @param userName username of the account
     * @return the list of accounts, or null if the account does not exist
     */
    public static List<Account> getAccountsByUserName(String userName) {
        try {
            return AccountRepository.getAllAccountsByUserName(userName);
        } catch (UserNotFoundException e) {
            return null;
        }
    }
}



