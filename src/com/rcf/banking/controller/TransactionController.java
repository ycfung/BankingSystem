package com.rcf.banking.controller;

import com.rcf.banking.entity.Account;
import com.rcf.banking.service.AccountService;
import com.rcf.banking.service.TransactionService;
import com.rcf.banking.util.Constant;
import com.rcf.banking.util.Currency;
import com.rcf.banking.util.HostHolder;
import com.rcf.banking.util.Input;


/**
 * The controller for the transaction
 *
 * @author Chengfeng RONG
 * @version 1.0
 */
public class TransactionController {

    /**
     * The method to deposit to a specific account
     *
     * @return 0 if the deposit is successful, -1 if the deposit is not successful
     */
    public static int deposit() {
        double amount;
        Currency currency;
        Account account;

        System.out.println("Please enter the currency you would like to deposit");
        currency = Input.inputCurrency();
        System.out.println("Please enter the amount you would like to deposit");
        amount = Input.inputDouble(0, 1000000);

        account = AccountService.getAccountByUserNameAndCurrency(HostHolder.getUserName(), currency);
        if (account == null) {
            System.out.println("You do not have an account in this currency");
            return -1;
        }

        int code = TransactionService.depositMoney(account, amount);
        if (code == 0) {
            System.out.println("The deposit is successful");
            System.out.println("Your new balance is " + account.getBalance() + " " + currency);
            return 0;
        } else {
            System.out.println("The deposit is not successful, please contact the staff");
            return -1;
        }
    }

    /**
     * The method to withdraw from a specific account
     *
     * @return 0 if the withdrawal is successful, -1 if the specific currency is not supported,
     * -2 if the available balance is not enough.
     */
    public static int withdraw() {
        // define some temp variables
        double amount;
        Currency currency;
        Account account;

        // get user input
        System.out.println("Please enter the currency you would like to withdraw");
        currency = Input.inputCurrency();
        System.out.println("Please enter the amount you would like to withdraw");
        amount = Input.inputDouble(0, 1000000);

        // check if the corresponding account exists
        account = AccountService.getAccountByUserNameAndCurrency(HostHolder.getUserName(), currency);
        if (account == null) {
            System.out.println("You do not have an account in this currency");
            return -1;
        }

        // withdraw the money
        double code = TransactionService.withdrawMoney(account, amount);
        if (code == 0) {
            System.out.println("You have successfully withdrawn " + amount + " " + currency);
            System.out.println("The withdrawal fee is " + amount * Constant.TRANSFER_FEE + " " + currency);
            System.out.println("Your new balance is " + account.getBalance() + " " + currency);
            return 0;
        } else {
            System.out.println("The withdrawal is not successful, you don't have enough balance");
            return -2;
        }
    }

    /**
     * The method to transfer money from one account to another
     *
     * @return 0 if the transfer is successful, -1 if the sender does not have an account in the currency,
     * -2 if the available balance is not enough, -3 if the receiver does not have an account in the currency.
     */
    public static int transfer() {
        // define some temp variables
        double amount;
        Currency currency;
        Account account;
        String receiver;
        Account receiverAccount;

        // get user input
        System.out.println("Please enter the currency you would like to transfer");
        currency = Input.inputCurrency();
        System.out.println("Please enter the amount you would like to transfer");
        amount = Input.inputDouble(0, 1000000);
        System.out.println("Please enter the user name of the account you would like to transfer to");
        receiver = Input.inputString();

        // check if the corresponding account exists
        account = AccountService.getAccountByUserNameAndCurrency(HostHolder.getUserName(), currency);
        if (account == null) {
            System.out.println("You do not have an account in this currency");
            return -1;
        }

        // check if the receiver account exists
        receiverAccount = AccountService.getAccountByUserNameAndCurrency(receiver, currency);
        if (receiverAccount == null) {
            System.out.println("The system did not find an account in this currency");
            return -3;
        }

        // transfer the money
        double code = TransactionService.transfer(account, amount, receiver);
        if (code == 0) {
            System.out.println("You have successfully transferred " + amount + " " + currency + " to " + receiver);
            System.out.println("The transfer fee is " + amount * Constant.TRANSFER_FEE + " " + currency);
            System.out.println("Your new balance is " + account.getBalance() + " " + currency);
            return 0;
        } else {
            System.out.println("The transfer is not successful, you don't have enough balance");
            return -2;
        }
    }

}
