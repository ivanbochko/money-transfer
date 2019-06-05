package com.ivanbochko.moneytransfer.account.service;

import com.google.inject.Singleton;
import com.ivanbochko.moneytransfer.account.AccountStorage;
import com.ivanbochko.moneytransfer.account.model.BankAccount;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

@Singleton
public class InMemoryAccountStorage implements AccountStorage {
    private final List<BankAccount> accounts = Collections.synchronizedList(new ArrayList<>());

    @Override
    public BankAccount create(BankAccount account) {
        boolean customerAccountAlreadyExists = accounts.stream()
                .anyMatch(new UniqueAccountPerCustomer(account));
        if (!customerAccountAlreadyExists) {
            accounts.add(account);
        }
        return account;
    }

    @Override
    public List<BankAccount> getAll() {
        return accounts;
    }

    private class UniqueAccountPerCustomer implements Predicate<BankAccount> {
        private final BankAccount newBankAccount;

        UniqueAccountPerCustomer(BankAccount newBankAccount) {
            this.newBankAccount = newBankAccount;
        }

        @Override
        public boolean test(BankAccount bankAccount) {
            return newBankAccount.getAccount().equals(bankAccount.getAccount())
                    && newBankAccount.getCustomer().equals(bankAccount.getCustomer());
        }
    }
}
