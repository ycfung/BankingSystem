package com.rcf.banking.entity;

import com.rcf.banking.util.Currency;
import com.rcf.banking.util.TransactionType;

import java.util.Date;

/**
 * This class represents a transfer fee transaction
 *
 * @author Chengfeng RONG
 * @version 1.0
 */
public class TransferFee extends Transaction {

    public TransferFee(String userName, Date date, Currency currency, double amount) {
        super(userName, date, currency, TransactionType.TRANSFER_FEE, amount);
    }

}
