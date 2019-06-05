package com.ivanbochko.moneytransfer.common;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.ivanbochko.moneytransfer.account.BankAccountCreator;
import com.ivanbochko.moneytransfer.account.model.BankAccount;
import com.ivanbochko.moneytransfer.common.model.Amount;
import com.ivanbochko.moneytransfer.common.model.Currency;
import com.ivanbochko.moneytransfer.common.model.Money;
import com.ivanbochko.moneytransfer.resources.account.CreateBankAccountRequest;
import com.ivanbochko.moneytransfer.transfer.BalanceReader;
import com.ivanbochko.moneytransfer.transfer.TransferProcessor;
import com.ivanbochko.moneytransfer.transfer.TransferResult;
import com.ivanbochko.moneytransfer.transfer.model.Transfer;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

public class MoneyTransferModuleTest {
    private BankAccountCreator bankAccountCreator;
    private BalanceReader balanceReader;
    private TransferProcessor transferProcessor;
    private BankAccount systemBankAccount;

    @Before
    public void setUp() {
        MoneyTransferAppConfig config = config();
        Injector injector = Guice.createInjector(new MainModule(config));

        bankAccountCreator = injector.getInstance(BankAccountCreator.class);
        balanceReader = injector.getInstance(BalanceReader.class);
        transferProcessor = injector.getInstance(TransferProcessor.class);
    }

    @Test
    public void shouldTransferMoneyFromSystemAccount() {
        //given
        BankAccount recipient = bankAccountCreator.create("John", "Savings", Currency.GBP);
        Transfer transfer = new Transfer(systemBankAccount, recipient, Amount.of(10.5));

        //when
        TransferResult transferResult = transferProcessor.process(transfer);

        //then
        assertThat(transferResult.isSuccessful()).isTrue();
        Money systemAccountBalance = balanceReader.getBalance(systemBankAccount);
        assertThat(systemAccountBalance).isEqualTo(new Money(Amount.of(-10.5), Currency.GBP));

        Money recipientBalance = balanceReader.getBalance(recipient);
        assertThat(recipientBalance).isEqualTo(new Money(Amount.of(10.5), Currency.GBP));
    }

    @Test
    public void shouldTransferMoneyBetweenUserAccounts() {
        //given
        BankAccount sender = bankAccountCreator.create("John", "Savings", Currency.GBP);
        BankAccount recipient = bankAccountCreator.create("Alice", "Savings", Currency.GBP);
        transferProcessor.process(new Transfer(systemBankAccount, sender, Amount.of(100)));

        //when
        TransferResult transferResult = transferProcessor.process(
                new Transfer(sender, recipient, Amount.of(70)));

        //then
        assertThat(transferResult.isSuccessful()).isTrue();

        Money senderBalance = balanceReader.getBalance(sender);
        assertThat(senderBalance).isEqualTo(new Money(Amount.of(30), Currency.GBP));

        Money recipientBalance = balanceReader.getBalance(recipient);
        assertThat(recipientBalance).isEqualTo(new Money(Amount.of(70), Currency.GBP));
    }

    @Test
    public void shouldTransferMoneyToSupportedExternalBank() {
        //given
        BankAccount sender = bankAccountCreator.create("John", "Savings", Currency.GBP);
        transferProcessor.process(new Transfer(systemBankAccount, sender, Amount.of(100)));
        BankAccount recipient = new BankAccount("Lloyds", "Alice", "Lloyds Savings", Currency.USD);

        //when
        TransferResult transferResult = transferProcessor.process(
                new Transfer(sender, recipient, Amount.of(70)));

        //then
        assertThat(transferResult.isSuccessful()).isTrue();

        Money senderBalance = balanceReader.getBalance(sender);
        assertThat(senderBalance).isEqualTo(new Money(Amount.of(30), Currency.GBP));
    }

    @Test
    public void shouldRejectTransferToUnsupportedExternalBank() {
        //given
        BankAccount sender = bankAccountCreator.create("John", "Savings", Currency.GBP);
        transferProcessor.process(new Transfer(systemBankAccount, sender, Amount.of(100)));
        BankAccount recipient = new BankAccount("Santander", "Ivan", "Savings", Currency.PLN);

        //when
        TransferResult transferResult = transferProcessor.process(
                new Transfer(sender, recipient, Amount.of(70)));

        //then
        assertThat(transferResult.isSuccessful()).isFalse();

        Money senderBalance = balanceReader.getBalance(sender);
        assertThat(senderBalance).isEqualTo(new Money(Amount.of(100), Currency.GBP));
    }

    @Test
    public void shouldTransferMoneyWithConversion() {
        //given
        BankAccount sender = bankAccountCreator.create("John", "Savings", Currency.GBP);
        BankAccount recipient = bankAccountCreator.create("Alice", "Savings", Currency.PLN);
        transferProcessor.process(new Transfer(systemBankAccount, sender, Amount.of(1000)));

        //when
        TransferResult transferResult = transferProcessor.process(
                new Transfer(sender, recipient, Amount.of(100)));

        //then
        assertThat(transferResult.isSuccessful()).isTrue();

        Money senderBalance = balanceReader.getBalance(sender);
        assertThat(senderBalance).isEqualTo(new Money(Amount.of(900), Currency.GBP));

        Money recipientBalance = balanceReader.getBalance(recipient);
        assertThat(recipientBalance).isEqualTo(new Money(Amount.of(500), Currency.PLN));
    }


    private MoneyTransferAppConfig config() {
        MoneyTransferAppConfig config = new MoneyTransferAppConfig();
        config.setBankIdentifier("Barklays");
        HashMap<Currency, Double> fxRates = new HashMap<>();
        fxRates.put(Currency.USD, 1.0);
        fxRates.put(Currency.GBP, 1.25);
        fxRates.put(Currency.EUR, 1.1);
        fxRates.put(Currency.PLN, 0.25);
        config.setFxRates(fxRates);
        CreateBankAccountRequest bankAccountRequest = CreateBankAccountRequest.builder()
                .account("Operators")
                .customer("Home Bank")
                .currency(Currency.GBP)
                .build();
        systemBankAccount = new BankAccount(config.getBankIdentifier(),
                bankAccountRequest.getCustomer(),
                bankAccountRequest.getAccount(),
                bankAccountRequest.getCurrency());
        config.setSystemBankAccount(bankAccountRequest);
        return config;
    }
}