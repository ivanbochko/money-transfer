package com.ivanbochko.moneytransfer.account.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ivanbochko.moneytransfer.account.AccountStorage;
import com.ivanbochko.moneytransfer.account.AllBankAccountsReader;
import com.ivanbochko.moneytransfer.account.model.BankAccount;

import java.util.List;

@Singleton
public class BankAccountsReader implements AllBankAccountsReader {
    private final AccountStorage accountStorage;

    @Inject
    BankAccountsReader(AccountStorage accountStorage) {
        this.accountStorage = accountStorage;
    }

    @Override
    public List<BankAccount> getAllAccounts() {
        return accountStorage.getAll();
    }
}
