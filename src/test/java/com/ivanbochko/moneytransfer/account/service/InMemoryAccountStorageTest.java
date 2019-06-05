package com.ivanbochko.moneytransfer.account.service;

import com.ivanbochko.moneytransfer.account.model.BankAccount;
import com.ivanbochko.moneytransfer.common.model.Currency;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class InMemoryAccountStorageTest {
    private InMemoryAccountStorage inMemoryAccountStorage = new InMemoryAccountStorage();

    @Test
    public void shouldKeepCreateAccounts() {
        BankAccount bankAccount = new BankAccount("Lloyds", "A", "B", Currency.USD);
        inMemoryAccountStorage.create(bankAccount);

        assertThat(inMemoryAccountStorage.getAll()).containsExactly(bankAccount);
    }

    @Test
    public void shouldKeepUniqueAccountPerCustomer() {
        BankAccount bankAccount1 = new BankAccount("Lloyds", "A", "B", Currency.USD);
        BankAccount bankAccount2 = new BankAccount("Lloyds", "A", "B", Currency.USD);
        BankAccount bankAccount3 = new BankAccount("Lloyds", "A", "C", Currency.USD);
        inMemoryAccountStorage.create(bankAccount1);
        inMemoryAccountStorage.create(bankAccount2);
        inMemoryAccountStorage.create(bankAccount3);
        inMemoryAccountStorage.create(bankAccount3);

        assertThat(inMemoryAccountStorage.getAll()).containsExactly(bankAccount1, bankAccount3);
    }
}