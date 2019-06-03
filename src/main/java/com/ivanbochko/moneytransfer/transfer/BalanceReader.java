package com.ivanbochko.moneytransfer.transfer;

import com.ivanbochko.moneytransfer.account.BankAccount;
import com.ivanbochko.moneytransfer.common.Money;

public interface BalanceReader {
    Money getBalance(BankAccount account);
}
