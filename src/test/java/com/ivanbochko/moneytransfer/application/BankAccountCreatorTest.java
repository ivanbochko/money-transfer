package com.ivanbochko.moneytransfer.application;

import com.ivanbochko.moneytransfer.transfer.BalanceReader;
import com.ivanbochko.moneytransfer.account.BankAccountCreator;
import com.ivanbochko.moneytransfer.account.BankAccountCreditor;
import com.ivanbochko.moneytransfer.common.Amount;
import com.ivanbochko.moneytransfer.account.BankAccount;
import com.ivanbochko.moneytransfer.common.Currency;
import com.ivanbochko.moneytransfer.common.Money;
import com.ivanbochko.moneytransfer.transfer.Transfer;
import com.ivanbochko.moneytransfer.transfer.TransferProcessor;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BankAccountCreatorTest {
    private BankAccountCreator bankAccountCreator;
    private BankAccountCreditor bankAccountCreditor;
    private BalanceReader balanceReader;
    private TransferProcessor transferProcessor;

    @Test
    public void shouldTransferMoneyForIntraBankOfSameCurrency() {
        BankAccount sender = bankAccountCreator.create("1", Currency.USD);
        bankAccountCreditor.credit(sender, new Money(Amount.of(20), Currency.USD));

        BankAccount recipient = bankAccountCreator.create("2", Currency.USD);


        Transfer transfer = new Transfer(sender, recipient, Amount.of(10.5));

        transferProcessor.process(transfer);

        Money senderBalance = balanceReader.getBalance(sender);
        assertThat(senderBalance).isEqualTo(new Money(Amount.of(9.5), Currency.USD));

        Money recipientBalance = balanceReader.getBalance(recipient);
        assertThat(recipientBalance).isEqualTo(new Money(Amount.of(10.5), Currency.USD));
    }
}