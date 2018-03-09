package com.atm.dao;

import java.math.BigDecimal;

public interface AccountDao {
    public BigDecimal withdrawAmount(String accountNumber, BigDecimal amount);
    public BigDecimal getBalance(String accountNumber);
    public boolean checkExists(String accountNumber);
}
