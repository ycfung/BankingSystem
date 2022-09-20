package com.rcf.banking;

import com.rcf.banking.entity.Account;
import com.rcf.banking.exception.AccountAlreadyExistsException;
import com.rcf.banking.repository.AccountRepository;
import com.rcf.banking.repository.TransactionRepository;
import com.rcf.banking.service.TransactionService;
import com.rcf.banking.util.Currency;
import com.rcf.banking.util.HostHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransactionServiceTest {
    @BeforeEach
    public void init() throws AccountAlreadyExistsException {
        AccountRepository.getAccounts().clear();
        TransactionRepository.getTransactions().clear();

        AccountRepository.getAccounts().add(new Account("OSL_FEE", Currency.USD, 0.0));
        AccountRepository.getAccounts().add(new Account("OSL_FEE", Currency.HKD, 0.0));
        AccountRepository.getAccounts().add(new Account("OSL_FEE", Currency.SGD, 0.0));

        Account alice = new Account("Alice", Currency.USD, 1000);
        AccountRepository.addAccount(alice);
        HostHolder.setUserName("Alice");
    }

    @Test
    public void testDepositMoney() {
        try {
            Account account = AccountRepository.getAccountByUserNameAndCurrency("Alice", Currency.USD);
            TransactionService.depositMoney(account, 100);
            assert account.getBalance() == 1100;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDepositMoneyToExistentAccount() {
        try {
            Account account = new Account("Bob", Currency.USD, 1000);
            int code = TransactionService.depositMoney(account, 100);
            assert code == -1;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testWithdrawMoney() {
        try {
            Account account = AccountRepository.getAccountByUserNameAndCurrency("Alice", Currency.USD);
            int code = TransactionService.withdrawMoney(account, 100);
            assert code == 0;
            assert account.getBalance() == 899.0;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testWithdrawMoneyFromNonexistentAccount() {
        try {
            Account account = new Account("Bob", Currency.USD, 1000);
            int code = TransactionService.withdrawMoney(account, 100);
            assert code == -1;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testWithdrawMoneyFromInsufficientBalance() {
        try {
            Account account = AccountRepository.getAccountByUserNameAndCurrency("Alice", Currency.USD);
            int code = TransactionService.withdrawMoney(account, 1000);
            assert code == -2;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testWithdrawMoneyTooFrequentWithdrawals() {
        try {
            Account account = AccountRepository.getAccountByUserNameAndCurrency("Alice", Currency.USD);
            int code = TransactionService.withdrawMoney(account, 1000);
            TransactionService.withdrawMoney(account, 100);
            TransactionService.withdrawMoney(account, 100);
            TransactionService.withdrawMoney(account, 100);
            TransactionService.withdrawMoney(account, 100);
            TransactionService.withdrawMoney(account, 100);
            code = TransactionService.withdrawMoney(account, 100);
            assert code == -3;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testTransfer() {
        try {
            Account account = AccountRepository.getAccountByUserNameAndCurrency("Alice", Currency.USD);
            Account account2 = new Account("Bob", Currency.USD, 1000);
            AccountRepository.addAccount(account2);
            int code = TransactionService.transfer(account, 100, account2.getUserName());
            assert code == 0;
            assert account.getBalance() == 899;
            assert account2.getBalance() == 1100;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testTransferWithNotEnoughBalance() {
        try {
            Account account = AccountRepository.getAccountByUserNameAndCurrency("Alice", Currency.USD);
            Account account2 = new Account("Bob", Currency.USD, 1000);
            AccountRepository.addAccount(account2);
            int code = TransactionService.transfer(account, 3000, account2.getUserName());
            assert code == -1;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testTransferToNonexistentAccount() {
        try {
            Account account = AccountRepository.getAccountByUserNameAndCurrency("Alice", Currency.USD);
            Account account2 = new Account("Bob", Currency.HKD, 1000);
            AccountRepository.addAccount(account2);
            int code = TransactionService.transfer(account, 100, account2.getUserName());
            assert code == -2;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
