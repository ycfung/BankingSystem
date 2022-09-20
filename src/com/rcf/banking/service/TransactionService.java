package com.rcf.banking.service;

import com.rcf.banking.entity.*;
import com.rcf.banking.exception.NotEnoughBalanceException;
import com.rcf.banking.exception.UserNotFoundException;
import com.rcf.banking.repository.AccountRepository;
import com.rcf.banking.repository.TransactionRepository;
import com.rcf.banking.util.Constant;

import java.awt.*;
import java.util.Date;

/**
 * This class provides services for transaction
 *
 * @author Chengfeng RONG
 * @version 1.0
 */
public class TransactionService {

    /**
     * Deposit money in the account
     *
     * @param account account to deposit money
     * @param amount  amount to deposit
     * @return -1 if the deposit is not successful, 0 if the deposit is successful
     */
    public static int depositMoney(Account account, double amount) {
        // create a new deposit transaction
        Deposit deposit = new Deposit(account.getUserName(), new Date(), account.getCurrency(), amount);
        try {
            // add the deposit transaction to the list of transactions
            TransactionRepository.addDepositRecord(deposit);

            // add the deposit amount to the available balance
            AccountRepository.addBalanceByUserNameAndCurrency(account.getUserName(), amount, account.getCurrency());
            return 0;
        } catch (UserNotFoundException e) {
            return -1;
        }

    }

    /**
     * Withdraw money from the account
     *
     * @param account account to withdraw money
     * @param amount  amount to withdraw
     * @return 0 if the withdrawal is successful, -1 if the user does not exist,
     * -2 if the user does not have enough balance, -3 if the user is not allowed to withdraw in 5 minutes
     */
    public static int withdrawMoney(Account account, double amount) {
        // create a withdrawal transaction and a withdrawal fee transaction
        Withdrawal withdrawal = new Withdrawal(account.getUserName(), new Date(), account.getCurrency(), -amount);
        WithdrawalFee withdrawalFee = new WithdrawalFee(account.getUserName(), new Date(), account.getCurrency(),
                -amount * Constant.WITHDRAWAL_FEE);

        try {
            /* A single client cannot do more than 5 withdrawals of any currency;
               limit to be reset every 5 minutes of last withdrawal */
            if (!TransactionRepository.canWithdraw(account.getUserName())) {
                return -3;
            }
            double balance = AccountRepository.getBalanceByUserNameAndCurrency(account.getUserName(), account.getCurrency());
            // check if the balance is enough to pay the withdrawal fee
            if (balance + withdrawalFee.getAmount() + withdrawalFee.getAmount() < 0) {
                return -1;
            } else {
                // add the withdrawal and withdrawal fee to the list of transactions
                TransactionRepository.addWithdrawalRecord(withdrawal, withdrawalFee);
                // subtract the money from the available balance
                AccountRepository.subtractBalanceByUserNameAndCurrency(account.getUserName(),
                        -withdrawal.getAmount() - withdrawalFee.getAmount(), account.getCurrency());
                // add the withdrawal fee to the built-in account
                AccountRepository.addBalanceByUserNameAndCurrency("OSL_FEE", -withdrawalFee.getAmount(), withdrawalFee.getCurrency());

                return 0;
            }
        } catch (UserNotFoundException e) {
            return -1;
        } catch (NotEnoughBalanceException e) {
            return -2;
        }
    }

    /**
     * Transfer money from one account to another
     * <p>
     * Actually, we need to make it an ACID Transaction, but for simplicity, we just do it in a few steps.
     * We can use a database transaction to make it ACID or a Spring transaction framework, but for this
     * project, I will only simulate the process.
     *
     * @param account   account to transfer money
     * @param amount    amount to transfer
     * @param toAccount account to receive money
     * @return -1 if the balance is not enough, -2 if the receiver does not have a corresponding account,
     * 0 if the transfer is successful
     */
    public static int transfer(Account account, double amount, String toAccount) {
        try {
            double balance = AccountRepository.getBalanceByUserNameAndCurrency(account.getUserName(), account.getCurrency());
            Account receiver = AccountRepository.getAccountByUserNameAndCurrency(toAccount, account.getCurrency());
            if (balance < amount + amount * Constant.TRANSFER_FEE) {
                // not enough balance
                throw new NotEnoughBalanceException();
            } else {
                // subtract the money from the available balance
                AccountRepository.subtractBalanceByUserNameAndCurrency(account.getUserName(),
                        amount + amount * Constant.TRANSFER_FEE, account.getCurrency());
                // add the money to the available balance of the receiver
                AccountRepository.addBalanceByUserNameAndCurrency(toAccount, amount, receiver.getCurrency());

                // create a transferIn, transferOut and transferFee transaction record
                TransferIn transferIn = new TransferIn(toAccount, new Date(), account.getCurrency(),
                        amount, account.getUserName());

                TransferOut transferOut = new TransferOut(account.getUserName(), new Date(), account.getCurrency(),
                        -amount, toAccount);

                TransferFee transferFee = new TransferFee(account.getUserName(), new Date(), account.getCurrency(),
                        -amount * Constant.TRANSFER_FEE);
                // add the transfer fee to the built-in account
                AccountRepository.addBalanceByUserNameAndCurrency("OSL_FEE", transferFee.getAmount(), transferFee.getCurrency());
                // add the transferIn, transferOut and transferFee to the list of transactions
                TransactionRepository.addTransferRecord(transferIn);
                TransactionRepository.addTransferRecord(transferOut);
                TransactionRepository.addTransferFeeRecord(transferFee);
                return 0;
            }
        } catch (UserNotFoundException e) {
            return -2;
        } catch (NotEnoughBalanceException e) {
            return -1;
        }
    }
}
