package com.ivanbochko.moneytransfer.account;

import com.google.inject.AbstractModule;
import com.ivanbochko.moneytransfer.account.service.BankAccountsReader;
import com.ivanbochko.moneytransfer.account.service.HomeBankAccountCreator;
import com.ivanbochko.moneytransfer.account.service.InMemoryAccountStorage;

public class AccountModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(BankAccountCreator.class).to(HomeBankAccountCreator.class);
        bind(AccountStorage.class).to(InMemoryAccountStorage.class);
        bind(AllBankAccountsReader.class).to(BankAccountsReader.class);
    }
}
