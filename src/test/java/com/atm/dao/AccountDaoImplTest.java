package com.atm.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

import java.math.BigDecimal;

public class AccountDaoImplTest {
    @InjectMocks
    AccountDao accountDao;

    @Before
    public void setUp(){
        accountDao = new AccountDaoImpl();
    }


    @Test
    public void checkExistsForValidAccountNumber(){
        //when
        boolean result = accountDao.checkExists("01001");
        //then
        assertEquals(true, result);
    }

    @Test
    public void checkExistsForInvalidAccountNumber(){
        //when
        boolean result = accountDao.checkExists("invalid");
        //then
        assertNotSame(true, result);
    }

    @Test
    public void getBalanceForAccountNumber(){
        //when
        BigDecimal result = accountDao.getBalance("01001");
        //then
        assertNotNull(result);
    }

    @Test
    public void withdrawAmount(){
        //given
        String accountNumber = "01001";
        BigDecimal initialBalance = accountDao.getBalance(accountNumber);
        //when
        BigDecimal result = accountDao.withdrawAmount(accountNumber, BigDecimal.valueOf(100));
        //then
        assertNotNull(result);
        assertNotSame(initialBalance, accountDao.getBalance(accountNumber));
    }
}
