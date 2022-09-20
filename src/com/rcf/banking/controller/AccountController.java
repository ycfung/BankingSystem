package com.rcf.banking.controller;

import com.rcf.banking.entity.Account;
import com.rcf.banking.entity.Transaction;
import com.rcf.banking.service.AccountService;
import com.rcf.banking.util.Currency;
import com.rcf.banking.util.HostHolder;
import com.rcf.banking.util.Input;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * The controller for the account
 *
 * @author Chengfeng RONG
 * @version 1.0
 */
public class AccountController {

    /**
     * The method to create a new account
     *
     * @return 0 if the account is created successfully, -1 if the account already exists
     */
    public static int openAccount() {
        String userName;
        Currency currency;

        System.out.println("Please enter the username:");
        userName = Input.inputString();
        System.out.println("Please enter the currency:");
        currency = Input.inputCurrency();

        int code = AccountService.openAccount(userName, currency);
        if (code == 0) {
            HostHolder.setUserName(userName);
            System.out.println("The account is opened successfully");
            return 0;
        } else {
            System.out.println("The account already exists");
            return -1;
        }
    }

    /**
     * The method to print the user's statement
     *
     * @return 0 if the statement is printed successfully, -1 if the user does not have any account
     */
    public static int displayStatement() {
        List<Transaction> transactions = AccountService.getStatement(HostHolder.getUserName());
        if (transactions == null) {
            System.out.println("The account does not exist");
            return -1;
        } else {
            // display client name and transaction details
            System.out.println("Client name: " + HostHolder.getUserName());

            SimpleDateFormat f = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
            System.out.printf("%-22s", "Date");
            System.out.printf("%-10s", "Currency");
            System.out.printf("%-16s", "Operation");
            System.out.printf("%-15s", "Amount");
            System.out.println();
            for (Transaction transaction : transactions) {
                System.out.printf("%-22s", f.format(transaction.getDate()));
                System.out.printf("%-10s", transaction.getCurrency());
                System.out.printf("%-16s", transaction.getOperation());
                if (transaction.getAmount() > 0) {
                    System.out.printf("%-15s", "+" + transaction.getAmount());
                } else {
                    System.out.printf("%-15s", transaction.getAmount());
                }
                System.out.println();
            }
            return 0;
        }
    }

    /**
     * The method to print the user's account balance
     *
     * @return 0 if the balance is printed successfully, -1 if the user does not have any account
     */
    public static int printBalance() {
        List<Account> accounts = AccountService.getAccountsByUserName(HostHolder.getUserName());
        if (accounts != null) {
            System.out.printf("%-10s", "Currency");
            System.out.printf("%-10s", "Balance");
            System.out.println();
            for (Account account : accounts) {
                System.out.printf("%-10s", account.getCurrency());
                System.out.printf("%-10s", account.getBalance());
                System.out.println();
            }
            return 0;
        } else {
            System.out.println("You don't have any accounts.");
            return -1;
        }

    }

}
