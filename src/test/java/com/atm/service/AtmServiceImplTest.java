package com.atm.service;

import com.atm.dao.AtmDao;
import com.atm.dao.AtmDaoImpl;
import com.atm.service.atm.AtmService;
import com.atm.service.atm.AtmServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AtmServiceImplTest {
    @InjectMocks
    private AtmService atmService = new AtmServiceImpl();
    @Mock
    private AtmDao atmDao;

    private String accountNumber="01001";

    @Test
    public void withdrawAmountNotInMultiplesOfFive(){
        //when
        String result = atmService.withdraw(51, accountNumber);
        //then
        assertEquals("Withdrawals allowed in multiples of 5 only!", result);
    }

    @Test
    public void withdrawAmountWhenCashUnavailable(){
        //given
        new AtmDaoImpl().replenish(50);
        //when
        String result = atmService.withdraw(55, accountNumber);
        //then
        assertEquals("No cash available!", result);
    }

    @Test
    public void withdrawMoreAmountThanInAccount(){
        //given
        new AtmDaoImpl().replenish(50);
        //when
        String result = atmService.withdraw(40, "01002");
        //then
        assertEquals("Insufficient funds!", result);
    }

    @Test
    public void withdrawAmount(){
        //given
        new AtmDaoImpl().replenish(50);
        //when
        String result = atmService.withdraw(40, accountNumber);
        //then
        assertNotNull(result);
        Assert.assertTrue(result.contains("Please collect your cash..."));
    }


}
