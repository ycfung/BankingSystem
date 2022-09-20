package com.rcf.banking.repository;

import com.rcf.banking.entity.*;
import com.rcf.banking.exception.NotEnoughBalanceException;
import com.rcf.banking.exception.UserNotFoundException;
import com.rcf.banking.util.Currency;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contains the methods to access the database.
 * But in this case, the database is not implemented, so the methods simulate the database access.
 *
 * @author Chengfeng RONG
 * @version 1.0
 */
public class TransactionRepository {

    private static List<Transaction> transactions = new ArrayList<>();

    // getter
    public static List<Transaction> getTransactions() {
        return transactions;
    }

    /**
     * This method simulates the database access to deposit money.
     * 1) It adds the transaction to the list of transactions.
     * 2) It adds the money to the available balance.
     *
     * @param deposit the deposit transaction
     */
    public static void addDepositRecord(Deposit deposit) throws UserNotFoundException {
        // create a new deposit transaction
        transactions.add(deposit);

    }

    /**
     * This method simulates the database access to withdraw money.
     *
     * @param withdrawal    the withdrawal transaction
     * @param withdrawalFee the withdrawal fee
     * @return 0 if the transaction is successful
     */
    public static int addWithdrawalRecord(Withdrawal withdrawal, WithdrawalFee withdrawalFee) throws NotEnoughBalanceException, UserNotFoundException {
        // check if the user has enough money
        double balance = AccountRepository.getBalanceByUserNameAndCurrency(withdrawal.getUserName(), withdrawal.getCurrency());
        if (balance < (withdrawal.getAmount() + withdrawalFee.getAmount())) {
            throw new NotEnoughBalanceException();
        }

        // add the withdrawal and withdrawal fee to the list of transactions
        transactions.add(withdrawal);
        transactions.add(withdrawalFee);

        return 0;
    }


    /**
     * This method simulates the database access to add a transferIn transaction.
     *
     * @param transferIn the transferIn transaction
     * @return 0 if the transaction is successful
     */
    public static int addTransferRecord(TransferIn transferIn) {
        transactions.add(transferIn);
        return 0;
    }

    /**
     * This method simulates the database access to add a transferIn transaction.
     *
     * @param transferOut the transferOut transaction
     * @return 0 if the transaction is successful
     */
    public static int addTransferRecord(TransferOut transferOut) {
        transactions.add(transferOut);
        return 0;
    }

    /**
     * This method simulates the database access to add a transfer fee transaction.
     *
     * @param transferFee the transfer fee transaction
     * @return 0 if the transaction is successful
     */
    public static int addTransferFeeRecord(TransferFee transferFee) {
        transactions.add(transferFee);
        return 0;
    }

    /**
     * This method simulates the database access to get the statement.
     *
     * @param userName the username
     * @return the statement
     */
    public static List<Transaction> getStatementByUserName(String userName) throws UserNotFoundException {
        if(AccountRepository.getAllAccountsByUserName(userName).size() == 0){
            throw new UserNotFoundException();
        }
        List<Transaction> statement = new ArrayList<>();
        for (Transaction transaction : transactions) {
            if (transaction.getUserName().equals(userName)) {
                statement.add(transaction);
            }
        }
        return statement;
    }

    /**
     * This method determines if the current client can withdraw money from the account.
     * Actually I am a little confused about the condition.
     * So here I suppose that if the client has more than 5 withdrawals on record, the client has to wait for 5 minutes
     * since last withdrawal
     *
     * @param userName the username
     * @return true if the client can withdraw money
     */
    public static boolean canWithdraw(String userName) {
        List<Transaction> withdraw = new ArrayList<>();
        for (Transaction transaction : transactions) {
            if (transaction.getUserName().equals(userName)) {
                if (transaction instanceof Withdrawal && withdraw.size() < 5) {
                    withdraw.add(transaction);
                } else if (withdraw.size() == 5) {
                    break;
                }
            }
        }
        if (withdraw.size() < 5) {
            return true;
        } else {
            long lastWithdrawTime = withdraw.get(withdraw.size() - 1).getDate().getTime();
            long currentTime = System.currentTimeMillis();
            return (currentTime - lastWithdrawTime) > 300000;
        }
    }
}