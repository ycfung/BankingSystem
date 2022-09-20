package com.rcf.banking.entity;

import com.rcf.banking.util.Currency;
import com.rcf.banking.util.TransactionType;

import java.util.Date;

/**
 * This class represents a withdrawal fee transaction
 * @author Chengfeng RONG
 * @version 1.0
 */
public class WithdrawalFee extends Transaction{
    public WithdrawalFee(String userName,Date date, Currency currency, double amount) {
        super( userName,date, currency, TransactionType.WITHDRAW_FEE, amount);
    }
}
