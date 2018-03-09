package com.atm.service;

import com.atm.dao.AccountDao;
import com.atm.model.Account;
import com.atm.service.account.AccountService;
import com.atm.service.account.AccountServiceImpl;
import org.junit.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceImplTest {
    @InjectMocks
    private AccountService accountService = new AccountServiceImpl();

    @Mock
    private AccountDao accountDao;

    @Mock
    private Account account;

    private static final String ACCOUNT_NUMBER = "ABC";

    @Before
    public void setUp(){
        account = new Account(ACCOUNT_NUMBER, BigDecimal.valueOf(123.5));
    }

    @Test
    public void checkBalance() {
        //given
        BigDecimal balance = account.getAmount();
        when(accountDao.getBalance(ACCOUNT_NUMBER)).thenReturn(balance);

        //when
        BigDecimal result = accountService.checkBalance(ACCOUNT_NUMBER);

        //then
        assertNotNull(result);
        assertSame(result, balance);
    }

    @Test
    public void checkExistsForValidAccountNumber(){
        //given
        String validAccountNumber = "01001";
        when(accountDao.checkExists(validAccountNumber)).thenReturn(true);
        //when
        boolean result = accountService.accountExists("01001");

        //then
        Assert.assertNotSame(false, result);
    }

    @Test
    public void checkExistsForInvalidAccountNumber(){
        //given
        String inValidAccountNumber = "ABC";
        when(accountDao.checkExists(inValidAccountNumber)).thenReturn(false);
        //when
        boolean result = accountService.accountExists(inValidAccountNumber);

        //then
        Assert.assertNotSame(true, result);
    }

    @Test
    public void withdrawAmount() {
        //given
        BigDecimal amount = BigDecimal.valueOf(50);
        when(accountDao.withdrawAmount(ACCOUNT_NUMBER, amount))
                .thenReturn(account.getAmount().subtract(amount));

        //when
        BigDecimal result = accountService.withdrawAmount(ACCOUNT_NUMBER, amount);

        //then
        assertNotNull(result);
        Assert.assertNotSame(BigDecimal.ZERO, result);
    }

}
