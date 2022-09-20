package com.rcf.banking.entity;

import com.rcf.banking.util.Currency;

/**
 * This is the entity class of a user account.
 *
 * @author Chengfeng RONG
 * @version 1.0
 */
public class Account {

    private String userName;

    private Currency Currency;

    private double balance;


    /**
     * Constructor of the Account class.
     * @param userName the client username
     * @param currency the currency of this account
     * @param balance the available currency balance
     */
    public Account(String userName, com.rcf.banking.util.Currency currency, double balance) {
        this.userName = userName;
        Currency = currency;
        this.balance = balance;
    }

    /* Getters and setters of the class. */

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Currency getCurrency() {
        return Currency;
    }

    public void setCurrency(Currency currency) {
        Currency = currency;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Account{" +
                "userName='" + userName + '\'' +
                ", Currency=" + Currency +
                ", balance=" + balance +
                '}';
    }
}
