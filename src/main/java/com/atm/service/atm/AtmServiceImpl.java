package com.atm.service.atm;

import com.atm.dao.AtmDao;
import com.atm.dao.AtmDaoImpl;
import com.atm.model.Atm;
import com.atm.model.Balance;
import com.atm.service.account.AccountService;
import com.atm.service.account.AccountServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

public class AtmServiceImpl implements AtmService {
    private static final Logger LOGGER = LogManager.getLogger("AtmServiceImpl");
    private static AtmDao atmDao = new AtmDaoImpl();

    @Override
    public String withdraw(int amount, String accountNumber) {
        AccountService accountService = new AccountServiceImpl();
        if (accountService.checkBalance(accountNumber).intValue() < amount) {
            return "Insufficient funds!";
        }
        LOGGER.debug("Getting cash from ATM for the amount {}", amount);
        if (amount % 5 != 0) {
            return "Withdrawals allowed in multiples of 5 only!";
        }
        Atm atm = atmDao.getTotalAmount();
        if (amount > atm.getAmount()) {
            return "No cash available!";
        }

        StringBuffer message = new StringBuffer("Please collect your cash...").append("\n");

        int requestedAmount = amount;
        Balance balance = atm.getBalance();
        Map<Integer, Integer> map = new LinkedHashMap<>();
        map.put(50, balance.getFifty());
        map.put(20, balance.getTwenty());
        map.put(10, balance.getTen());
        map.put(5, balance.getFive());

        int numberOfNotes, notes, count;
        int[] denomination = new int[map.size()];
        boolean flag = false;

        /*if (map.get(5) > 0) {
            amount = amount - 5;
            flag = true;
            map.put(5, map.get(5)-1);
        }*/

        int i = 0;
        for (Integer key : map.keySet()) {
            notes = key;
            count = map.get(key);
            numberOfNotes = amount / notes;
            if (count>0 && count >= numberOfNotes) {
                amount = amount % notes;
            } else if (count > 0) {
                numberOfNotes = numberOfNotes - count;
                amount = (amount - (numberOfNotes * notes));
                numberOfNotes = count;
            } else {
                numberOfNotes = 0;
            }
            if (flag && notes == 5) {
                numberOfNotes = numberOfNotes + 1;
            }

            denomination[i] = numberOfNotes;
            i++;
        }

        if (amount != 0) {
            return "No cash available!";
        } else {
            i = 0;
            for (Integer key : map.keySet()) {
                buildMessage(message, denomination[i], () -> (new StringBuffer("*").append("£" + key).append(" ")));
                map.put(key, map.get(key) - denomination[i]);
                i++;
            }
            accountService.withdrawAmount(accountNumber, BigDecimal.valueOf(requestedAmount));
            Balance updateBalance = new Balance(map.get(50), map.get(20), map.get(10), map.get(5));

            atmDao.updateAmount(atm.getAmount() - requestedAmount, updateBalance);
        }
        return message.toString();
    }


    private StringBuffer buildMessage(StringBuffer message, int value, Supplier supplier) {
        LOGGER.debug("Building return message");
        if (value > 0) {
            return message.append(value).append(supplier.get());
        }
        return message;
    }
}
