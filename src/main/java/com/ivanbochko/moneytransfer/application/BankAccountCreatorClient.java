package com.ivanbochko.moneytransfer.application;

import com.ivanbochko.moneytransfer.model.BankAccount;
import com.ivanbochko.moneytransfer.model.Currency;

public interface BankAccountCreatorClient {

    BankAccount create(String customer, Currency currency);
}
