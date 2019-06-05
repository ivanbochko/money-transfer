package com.ivanbochko.moneytransfer.account.service;

import com.ivanbochko.moneytransfer.account.AccountStorage;
import com.ivanbochko.moneytransfer.account.model.BankAccount;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class BankAccountsReaderTest {
    private AccountStorage accountStorage = mock(AccountStorage.class);
    private BankAccountsReader reader = new BankAccountsReader(accountStorage);

    @Test
    public void shouldDelegateCallToStorage() {
        given(accountStorage.getAll()).willReturn(Collections.emptyList());

        List<BankAccount> allAccounts = reader.getAllAccounts();

        assertThat(allAccounts).isEmpty();
        verify(accountStorage).getAll();
    }
}