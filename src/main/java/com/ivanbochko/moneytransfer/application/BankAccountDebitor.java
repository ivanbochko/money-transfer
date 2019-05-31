package com.ivanbochko.moneytransfer.application;

import com.ivanbochko.moneytransfer.model.Amount;
import com.ivanbochko.moneytransfer.model.BankAccount;
import com.ivanbochko.moneytransfer.model.Money;

public interface BankAccountDebitor {
    void debit(BankAccount account, Money money);
}
