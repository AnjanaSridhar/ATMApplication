package com.atm.model;

import java.math.BigDecimal;

public class Account {
    private String accountNumber;
    private BigDecimal amount;
    public Account(){}

    public Account(String accountNumber, BigDecimal amount){
        this.accountNumber = accountNumber;
        this.amount = amount;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
