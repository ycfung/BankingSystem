package com.rcf.banking.entity;

import com.rcf.banking.util.Currency;
import com.rcf.banking.util.TransactionType;

import java.util.Date;

/**
 * This class represents an incoming transfer
 *
 * @author Chengfeng RONG
 * @version 1.0
 */
public class TransferIn extends Transaction {

    private String fromAccount;

    public TransferIn(String userName, Date date, Currency currency, double amount, String fromAccount) {
        super(userName, date, currency, TransactionType.TRANSFER_IN, amount);
        this.fromAccount = fromAccount;
    }

    public String getFromAccount() {
        return fromAccount;
    }

}
