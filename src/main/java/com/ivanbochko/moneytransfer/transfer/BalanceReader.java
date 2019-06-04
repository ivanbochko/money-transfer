package com.ivanbochko.moneytransfer.transfer;

import com.ivanbochko.moneytransfer.account.model.BankAccount;
import com.ivanbochko.moneytransfer.common.model.Money;

public interface BalanceReader {
    Money getBalance(BankAccount account);
}
