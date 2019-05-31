package com.ivanbochko.moneytransfer.application;

import com.ivanbochko.moneytransfer.model.BankAccount;
import com.ivanbochko.moneytransfer.model.Money;

public interface BankAccountBalanceReader {
    Money getBalance(BankAccount account);
}
