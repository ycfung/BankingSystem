package com.rcf.banking.entity;

import com.rcf.banking.util.Currency;
import com.rcf.banking.util.TransactionType;

import java.util.Date;

/**
 * This class represents an outgoing transfer
 *
 * @author Chengfeng RONG
 * @version 1.0
 */
public class TransferOut extends Transaction {

    private String toAccount;

    public TransferOut(String userName, Date date, Currency currency, double amount, String toAccount) {
        super(userName, date, currency, TransactionType.TRANSFER_OUT, amount);
        this.toAccount = toAccount;
    }

    public String getToAccount() {
        return toAccount;
    }
}
