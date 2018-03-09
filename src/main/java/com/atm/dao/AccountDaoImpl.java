package com.atm.dao;

import com.atm.model.Account;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

public class AccountDaoImpl implements AccountDao {
    private static final Logger LOGGER = LogManager.getLogger("AccountDaoImpl");
    private static final String accountFile = "Account.json";


    @Override
    public BigDecimal withdrawAmount(String accountNumber, BigDecimal amount) {
        BigDecimal balance = BigDecimal.ZERO;
        for (Account account : readData()) {
            if (accountNumber.equals(account.getAccountNumber())) {
                balance = account.getAmount().subtract(amount);
                account.setAmount(balance);
                updateData(account);
            }
        }
        LOGGER.debug("Balance after withdrawal is {}", balance);
        return balance;
    }

    @Override
    public BigDecimal getBalance(String accountNumber) {
        for (Account account : readData()) {
            if (accountNumber.equals(account.getAccountNumber())) {
                return account.getAmount();
            }
        }
        return BigDecimal.ZERO;
    }

    @Override
    public boolean checkExists(String accountNumber) {
        for (Account account : readData()) {
            if (accountNumber.equals(account.getAccountNumber())) {
                LOGGER.debug("Account exists!");
                return true;
            }
        }
        LOGGER.debug("Account does not exist!");
        return false;
    }


    private static Account[] readData() {
        LOGGER.debug("Reading from Account json file");
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new File(ClassLoader.getSystemClassLoader().
                    getResource(accountFile).getPath()), Account[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static void updateData(Account account) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Account[] accountList = mapper.readValue(new File(ClassLoader.getSystemClassLoader().
                    getResource(accountFile).getPath()), Account[].class);
            for (Account account1 : accountList) {
                if (account.getAccountNumber().equals(account1.getAccountNumber())) {
                    account1.setAmount(account.getAmount());
                }
            }
            mapper.writeValue(new File(ClassLoader.getSystemClassLoader().
                    getResource(accountFile).getPath()), accountList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
