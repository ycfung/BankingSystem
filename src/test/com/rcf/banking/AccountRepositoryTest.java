package com.rcf.banking;

import com.rcf.banking.entity.Account;
import com.rcf.banking.exception.AccountAlreadyExistsException;
import com.rcf.banking.exception.NotEnoughBalanceException;
import com.rcf.banking.exception.UserNotFoundException;
import com.rcf.banking.repository.AccountRepository;
import com.rcf.banking.util.Currency;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AccountRepositoryTest {

    @BeforeEach
    public void init() {
        AccountRepository.getAccounts().clear();
    }

    @Test
    public void testAddAccount() {
        Account account = new Account("Chengfeng", Currency.USD, 1000);
        try {
            int code = AccountRepository.addAccount(account);
            assert code == 0;
        } catch (AccountAlreadyExistsException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testAddDuplicatedAccount() {
        Account account = new Account("Chengfeng", Currency.USD, 1000);
        Assertions.assertThrows(AccountAlreadyExistsException.class, () -> {
            AccountRepository.addAccount(account);
            AccountRepository.addAccount(account);
        });
    }

    @Test
    public void testGetAccountByUserNameAndCurrency() {
        Account account = new Account("Chengfeng", Currency.USD, 1000);
        try {
            AccountRepository.addAccount(account);
            assert AccountRepository.getAccountByUserNameAndCurrency("Chengfeng", Currency.USD) == account;
        } catch (AccountAlreadyExistsException | UserNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetNonexistentAccountByUserNameAndCurrency() {
        Assertions.assertThrows(UserNotFoundException.class, () ->
                AccountRepository.getAccountByUserNameAndCurrency("Chengfeng", Currency.USD));
    }

    @Test
    public void testGetBalanceByUserNameAndCurrency() {
        Account account = new Account("Chengfeng", Currency.USD, 1000);
        try {
            AccountRepository.addAccount(account);
            assert AccountRepository.getBalanceByUserNameAndCurrency("Chengfeng", Currency.USD) == 1000;
        } catch (AccountAlreadyExistsException | UserNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetNonExistentBalanceByUserNameAndCurrency() {
        Assertions.assertThrows(UserNotFoundException.class, () ->
                AccountRepository.getBalanceByUserNameAndCurrency("Chengfeng", Currency.USD));
    }

    @Test
    public void testAddBalanceByUserNameAndCurrency() {
        Account account = new Account("Chengfeng", Currency.USD, 1000);
        try {
            AccountRepository.addAccount(account);
            AccountRepository.addBalanceByUserNameAndCurrency("Chengfeng", 1000, Currency.USD);
            assert AccountRepository.getBalanceByUserNameAndCurrency("Chengfeng", Currency.USD) == 2000;
        } catch (AccountAlreadyExistsException | UserNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAddBalanceByUserNameAndCurrencyToNonExistentUser() {
        Assertions.assertThrows(UserNotFoundException.class, () ->
                AccountRepository.addBalanceByUserNameAndCurrency("Chengfeng", 1000, Currency.USD));
    }

    @Test
    public void testSubtractBalanceByUserNameAndCurrency() {
        Account account = new Account("Chengfeng", Currency.USD, 1000);
        try {
            AccountRepository.addAccount(account);
            AccountRepository.subtractBalanceByUserNameAndCurrency("Chengfeng", 1000, Currency.USD);
            assert AccountRepository.getBalanceByUserNameAndCurrency("Chengfeng", Currency.USD) == 0;
        } catch (AccountAlreadyExistsException | UserNotFoundException | NotEnoughBalanceException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSubtractInsufficientBalanceByUserNameAndCurrency() {
        Account account = new Account("Chengfeng", Currency.USD, 1000);
        Assertions.assertThrows(NotEnoughBalanceException.class, () -> {
            AccountRepository.addAccount(account);
            AccountRepository.subtractBalanceByUserNameAndCurrency("Chengfeng", 2000, Currency.USD);
        });
    }

    @Test
    public void testSubtractBalanceByUserNameAndCurrencyToNonExistentUser() {
        Assertions.assertThrows(UserNotFoundException.class, () ->
                AccountRepository.subtractBalanceByUserNameAndCurrency("Chengfeng", 1000, Currency.USD));
    }

    @Test
    public void testGetAllAccountsByUserName() {
        Account account = new Account("Chengfeng", Currency.USD, 1000);
        Account account2 = new Account("Chengfeng", Currency.HKD, 1000);
        try {
            AccountRepository.addAccount(account);
            AccountRepository.addAccount(account2);
            assert AccountRepository.getAllAccountsByUserName("Chengfeng").size() == 2;
        } catch (AccountAlreadyExistsException | UserNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetAllAccountsByNonExistentUserName() {
        Assertions.assertThrows(UserNotFoundException.class, () ->
                AccountRepository.getAllAccountsByUserName("Chengfeng"));
    }
}
