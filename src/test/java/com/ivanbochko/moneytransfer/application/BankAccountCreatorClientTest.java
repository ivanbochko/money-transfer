package com.ivanbochko.moneytransfer.application;

import com.ivanbochko.moneytransfer.model.Amount;
import com.ivanbochko.moneytransfer.model.BankAccount;
import com.ivanbochko.moneytransfer.model.Currency;
import com.ivanbochko.moneytransfer.model.Money;
import com.ivanbochko.moneytransfer.model.MoneyTransfer;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BankAccountCreatorClientTest {
    private BankAccountCreatorClient bankAccountCreator;
    private BankAccountCreditor bankAccountCreditor;
    private BankAccountBalanceReader bankAccountBalanceReader;
    private TransferService transferService;

    @Test
    public void shouldTransferMoneyForIntraBankOfSameCurrency() {
        BankAccount sender = bankAccountCreator.create("1", Currency.USD);
        bankAccountCreditor.credit(sender, new Money(Amount.of(20), Currency.USD));

        BankAccount recipient = bankAccountCreator.create("2", Currency.USD);


        MoneyTransfer transfer = new MoneyTransfer(sender, recipient, Amount.of(10.5));

        transferService.make(transfer);

        Money senderBalance = bankAccountBalanceReader.getBalance(sender);
        assertThat(senderBalance).isEqualTo(new Money(Amount.of(9.5), Currency.USD));

        Money recipientBalance = bankAccountBalanceReader.getBalance(recipient);
        assertThat(recipientBalance).isEqualTo(new Money(Amount.of(10.5), Currency.USD));
    }
}