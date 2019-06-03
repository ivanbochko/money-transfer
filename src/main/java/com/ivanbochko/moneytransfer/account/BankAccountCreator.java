package com.ivanbochko.moneytransfer.account;

import com.ivanbochko.moneytransfer.common.Currency;

public interface BankAccountCreator {

    BankAccount create(String customer, Currency currency);
}
