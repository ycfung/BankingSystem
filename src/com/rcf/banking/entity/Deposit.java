package com.rcf.banking.entity;

import com.rcf.banking.util.Currency;
import com.rcf.banking.util.TransactionType;

import java.util.Date;

/**
 * This class represents a deposit transaction
 *
 * @author Chengfeng RONG
 * @version 1.0
 */
public class Deposit extends Transaction {


    public Deposit(String userName, Date date, Currency currency, double amount) {
        super(userName, date, currency, TransactionType.DEPOSIT, amount);
    }

}
