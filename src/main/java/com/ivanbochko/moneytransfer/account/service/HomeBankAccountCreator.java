package com.ivanbochko.moneytransfer.account.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ivanbochko.moneytransfer.account.AccountStorage;
import com.ivanbochko.moneytransfer.account.BankAccountCreator;
import com.ivanbochko.moneytransfer.account.model.BankAccount;
import com.ivanbochko.moneytransfer.common.model.Currency;
import com.ivanbochko.moneytransfer.resources.account.CreateBankAccountRequest;

import javax.inject.Named;

@Singleton
public class HomeBankAccountCreator implements BankAccountCreator {
    private final String hostBankIdentifier;
    private final AccountStorage accountStorage;

    @Inject
    HomeBankAccountCreator(@Named("bankIdentifier") String hostBankIdentifier,
                           @Named("systemBankAccountRequest") CreateBankAccountRequest systemBankAccount,
                           AccountStorage accountStorage) {
        this.hostBankIdentifier = hostBankIdentifier;
        this.accountStorage = accountStorage;

        create(systemBankAccount.getCustomer(),
                systemBankAccount.getAccount(),
                systemBankAccount.getCurrency());
    }

    @Override
    public BankAccount create(String customer, String account, Currency currency) {
        BankAccount bankAccount = new BankAccount(hostBankIdentifier, customer, account, currency);
        accountStorage.create(bankAccount);
        return bankAccount;
    }
}
