package com.atm.dao;

import com.atm.model.Atm;
import com.atm.model.Balance;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;

public class AtmDaoImpl implements AtmDao {

    private static final Logger LOGGER = LogManager.getLogger("AtmDaoImpl");
    private static final String ATMFile = "ATM.json";

    private static Atm atm;

    @Override
    public void updateAmount(int amount, Balance balance) {
        LOGGER.debug("Updating balance in ATM after withdrawal");
        Atm atm = new Atm(balance, amount);
        writeData(atm);
    }

    @Override
    public Atm getTotalAmount() {
        LOGGER.debug("Retrieving amount from ATM");
        return readData();
    }

    public void replenish(int initAmount) {
        LOGGER.debug("Replenish the ATM");
        int amount = initAmount;
        int fifty = (amount * 20 / 100) / 50;
        amount = amount - (fifty * 50);
        int twenty = (amount * 25 / 100) / 20;
        amount = amount - (twenty * 20);
        int ten = (amount * 30 / 100) / 10;
        amount = amount - (ten * 10);
        int five = amount / 5;

        Balance balance = new Balance(fifty, twenty, ten, five);

        atm = new Atm(balance, initAmount);

        writeData(atm);
    }

    private static void writeData(Atm atm) {
        LOGGER.debug("Writing data to json for ATM");
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File(ClassLoader.getSystemClassLoader().getResource(ATMFile).getPath()), atm);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Atm readData() {
        LOGGER.debug("Reading data from ATM json");
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new File(ClassLoader.getSystemClassLoader().getResource(ATMFile).getPath()), Atm.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
