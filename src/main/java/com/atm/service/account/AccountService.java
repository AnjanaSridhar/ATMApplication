package com.atm.service.account;

import java.math.BigDecimal;

public interface AccountService {

    public BigDecimal checkBalance(String accountNumber);

    public BigDecimal withdrawAmount(String accountNumber, BigDecimal amount);

    public boolean accountExists(String accountNumber);
}
