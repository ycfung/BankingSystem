package com.rcf.banking.util;

/**
 * This interface defines the transaction types.
 */
public interface TransactionType {

    String DEPOSIT = "Deposit";
    String WITHDRAWAL = "Withdrawal";
    String WITHDRAW_FEE = "Withdrawal Fee";
    String TRANSFER_IN = "Transfer In";
    String TRANSFER_OUT = "Transfer Out";
    String TRANSFER_FEE = "Transfer Fee";

}
