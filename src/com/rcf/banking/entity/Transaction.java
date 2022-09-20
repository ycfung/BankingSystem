package com.rcf.banking.entity;

import com.rcf.banking.util.Currency;

import java.util.Date;

/**
 * Transaction contains deposit, withdraw, transfer and also the corresponding fees.
 *
 * @author Chengfeng RONG
 * @version 1.0
 */
public abstract class Transaction {

    private String userName;

    private Date date;

    private Currency currency;

    private String operation;

    private double amount;

    // Constructor of the class.
    public Transaction(String userName, Date date, Currency currency, String operation, double amount) {
        this.userName = userName;
        this.date = date;
        this.currency = currency;
        this.operation = operation;
        this.amount = amount;
    }

    /* Getters and setters of the class. */

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "userName='" + userName + '\'' +
                ", date=" + date +
                ", currency=" + currency +
                ", operation='" + operation + '\'' +
                ", amount=" + amount +
                '}';
    }
}
