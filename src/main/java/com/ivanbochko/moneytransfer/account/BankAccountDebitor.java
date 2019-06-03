package com.ivanbochko.moneytransfer.account;

import com.ivanbochko.moneytransfer.common.Money;

public interface BankAccountDebitor {
    void debit(BankAccount account, Money money);
}
