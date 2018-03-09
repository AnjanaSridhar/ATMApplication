package com.atm.dao;

import com.atm.model.Atm;
import com.atm.model.Balance;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

public class AtmDaoImplTest {
    @InjectMocks
    private AtmDao atmDao;

    @Before
    public void setUp(){
        atmDao = new AtmDaoImpl();
    }

    @Test
    public void getAmount(){
        //when
        Atm result = atmDao.getTotalAmount();
        //then
        Assert.assertNotNull(result);
    }

    @Test
    public void updateAmount(){
        //given
        int initialAmount = atmDao.getTotalAmount().getAmount();
        Atm atmObj = new Atm(new Balance(0, 0, 1, 2), 20);
        //when
        atmDao.updateAmount(atmObj.getAmount(), atmObj.getBalance());
        //then
        Assert.assertNotSame(initialAmount, atmDao.getTotalAmount());
    }

}
