package com.ivanbochko.moneytransfer.account;

import com.ivanbochko.moneytransfer.account.model.BankAccount;
import com.ivanbochko.moneytransfer.common.model.Currency;

public interface BankAccountCreator {

    BankAccount create(String customer, String account, Currency currency);
}
