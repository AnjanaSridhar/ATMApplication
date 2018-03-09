package com.atm.dao;

import com.atm.model.Atm;
import com.atm.model.Balance;

public interface AtmDao {
    public void updateAmount(int amount, Balance balance);
    public Atm getTotalAmount();
}
