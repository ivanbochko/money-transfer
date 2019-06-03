package com.ivanbochko.moneytransfer.account;

import com.ivanbochko.moneytransfer.common.Money;

public interface BankAccountCreditor {
    void credit(BankAccount account, Money money);
}
