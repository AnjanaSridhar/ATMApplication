package com.atm.model;

public class Atm {
    private Balance balance;
    private int amount;

    public Atm(){}

    public Atm(Balance balance, int amount) {
        this.balance = balance;
        this.amount = amount;
    }

    public Balance getBalance() {
        return balance;
    }

    public int getAmount() {
        return amount;
    }

    public void setBalance(Balance balance) {
        this.balance = balance;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
