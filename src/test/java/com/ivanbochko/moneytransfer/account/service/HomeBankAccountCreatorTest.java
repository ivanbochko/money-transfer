package com.ivanbochko.moneytransfer.account.service;

import com.ivanbochko.moneytransfer.account.AccountStorage;
import com.ivanbochko.moneytransfer.account.model.BankAccount;
import com.ivanbochko.moneytransfer.common.model.Currency;
import com.ivanbochko.moneytransfer.resources.account.CreateBankAccountRequest;
import org.junit.Test;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class HomeBankAccountCreatorTest {
    private AccountStorage accountStorage = mock(AccountStorage.class);

    @Test
    public void shouldEnsureSystemAccountExists() {
        BankAccount systemAccount = new BankAccount("Home Bank", "Operator", "Unlimited", Currency.USD);
        given(accountStorage.create(systemAccount)).willReturn(systemAccount);

        new HomeBankAccountCreator("Home Bank",
                new CreateBankAccountRequest("Operator", "Unlimited", Currency.USD),
                accountStorage);

        verify(accountStorage).create(eq(systemAccount));
    }
}