package com.rcf.banking;

import com.rcf.banking.entity.*;
import com.rcf.banking.exception.AccountAlreadyExistsException;
import com.rcf.banking.repository.AccountRepository;
import com.rcf.banking.repository.TransactionRepository;
import com.rcf.banking.util.Currency;
import com.rcf.banking.util.HostHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class TransactionRepositoryTest {


    @BeforeEach
    public void init() throws AccountAlreadyExistsException {
        TransactionRepository.getTransactions().clear();
        AccountRepository.getAccounts().clear();
        AccountRepository.addAccount(new Account("Alice", Currency.USD, 1000));
        AccountRepository.addAccount(new Account("Bob", Currency.USD, 1000));
        AccountRepository.addAccount(new Account("Bob", Currency.HKD, 1000));
        HostHolder.setUserName("Bob");
    }

    @Test
    public void testAddDepositRecord() {
        try {
            TransactionRepository.addDepositRecord(new Deposit("Bob", new Date(), Currency.USD, 100));
            assert TransactionRepository.getStatementByUserName("Bob").size() == 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAddWithdrawalRecord() {
        try {
            Withdrawal withdrawal = new Withdrawal("Bob", new Date(), Currency.USD, 100);
            WithdrawalFee withdrawalFee = new WithdrawalFee("Bob", new Date(), Currency.USD, 1);
            int code = TransactionRepository.addWithdrawalRecord(withdrawal, withdrawalFee);
            assert code == 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAddTransferRecordOut() {
        try {
            int code = TransactionRepository.addTransferRecord(new TransferOut("Bob", new Date(), Currency.USD, 100, "Alice"));
            assert code == 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAddTransferRecordIn() {
        try {
            int code = TransactionRepository.addTransferRecord(new TransferIn("Bob", new Date(), Currency.USD, 100, "Alice"));
            assert code == 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetStatementByUserName() {
        try {
            TransactionRepository.addDepositRecord(new Deposit("Bob", new Date(), Currency.USD, 100));
            TransactionRepository.addWithdrawalRecord(new Withdrawal("Bob", new Date(), Currency.USD, 100), new WithdrawalFee("Bob", new Date(), Currency.USD, 1));
            TransactionRepository.addTransferRecord(new TransferOut("Bob", new Date(), Currency.USD, 100, "Alice"));
            TransactionRepository.addTransferRecord(new TransferIn("Bob", new Date(), Currency.USD, 100, "Alice"));
            assert TransactionRepository.getStatementByUserName("Bob").size() == 5;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCanWithdraw() {
        try {
            TransactionRepository.addDepositRecord(new Deposit("Bob", new Date(), Currency.USD, 100));
            TransactionRepository.addWithdrawalRecord(new Withdrawal("Bob", new Date(), Currency.USD, 100), new WithdrawalFee("Bob", new Date(), Currency.USD, 1));
            TransactionRepository.addTransferRecord(new TransferOut("Bob", new Date(), Currency.USD, 100, "Alice"));
            TransactionRepository.addTransferRecord(new TransferIn("Bob", new Date(), Currency.USD, 100, "Alice"));
            assert TransactionRepository.canWithdraw("Bob");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCannotWithdraw() {
        try {
            TransactionRepository.addDepositRecord(new Deposit("Bob", new Date(), Currency.USD, 1000));
            TransactionRepository.addWithdrawalRecord(new Withdrawal("Bob", new Date(), Currency.USD, 100), new WithdrawalFee("Bob", new Date(), Currency.USD, 1));
            TransactionRepository.addWithdrawalRecord(new Withdrawal("Bob", new Date(), Currency.USD, 100), new WithdrawalFee("Bob", new Date(), Currency.USD, 1));
            TransactionRepository.addWithdrawalRecord(new Withdrawal("Bob", new Date(), Currency.USD, 100), new WithdrawalFee("Bob", new Date(), Currency.USD, 1));
            TransactionRepository.addWithdrawalRecord(new Withdrawal("Bob", new Date(), Currency.USD, 100), new WithdrawalFee("Bob", new Date(), Currency.USD, 1));
            TransactionRepository.addWithdrawalRecord(new Withdrawal("Bob", new Date(), Currency.USD, 100), new WithdrawalFee("Bob", new Date(), Currency.USD, 1));
            assert !TransactionRepository.canWithdraw("Bob");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
