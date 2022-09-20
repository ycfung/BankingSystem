package com.rcf.banking;

import com.rcf.banking.entity.Transaction;
import com.rcf.banking.repository.AccountRepository;
import com.rcf.banking.service.AccountService;
import com.rcf.banking.service.TransactionService;
import com.rcf.banking.util.Currency;
import com.rcf.banking.util.HostHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class AccountServiceTest {
    @BeforeEach
    public void init() {
        AccountRepository.getAccounts().clear();
    }

    @Test
    public void testOpenAccount() {
        int code = AccountService.openAccount("Chengfeng", Currency.USD);
        assert code == 0;
    }

    @Test
    public void testOpenDuplicateAccount() {
        AccountService.openAccount("Chengfeng", Currency.USD);
        int code = AccountService.openAccount("Chengfeng", Currency.USD);
        assert code == -1;
    }

    @Test
    public void testGetStatement() {
        try {
            AccountService.openAccount("Chengfeng", Currency.USD);
            HostHolder.setUserName("Chengfeng");
            TransactionService.depositMoney(AccountRepository.getAccountByUserNameAndCurrency("Chengfeng", Currency.USD), 100);
            List<Transaction> list = AccountService.getStatement("Chengfeng");
            assert list.size() == 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetNonexistentStatement() {
        try {
            List<Transaction> list = AccountService.getStatement("xxx");
            assert list == null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetAccountByUserNameAndCurrency() {
        try {
            AccountService.openAccount("Chengfeng", Currency.USD);
            assert AccountService.getAccountByUserNameAndCurrency("Chengfeng", Currency.USD) != null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetNonexistentAccountByUserNameAndCurrency() {
        try {
            assert AccountService.getAccountByUserNameAndCurrency("Chengfeng", Currency.USD) == null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetAccountsByUserName() {
        try {
            AccountService.openAccount("Chengfeng", Currency.USD);
            AccountService.openAccount("Chengfeng", Currency.HKD);
            assert AccountService.getAccountsByUserName("Chengfeng").size() == 2;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetNonexistentAccountsByUserName() {
        try {
            assert AccountService.getAccountsByUserName("Chengfeng") == null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
