package com.ivanbochko.moneytransfer.transfer;

import com.ivanbochko.moneytransfer.account.BankAccount;
import com.ivanbochko.moneytransfer.common.Amount;
import com.ivanbochko.moneytransfer.common.Currency;
import org.junit.Test;

import static org.junit.Assert.*;

public class TransferProcessorTest {
    private TransferProcessor processor;

    @Test
    public void shouldCheckBalanceBeforeDebit() {
        Transfer transfer = new Transfer(
                new BankAccount("Barklays", "John", "Savings", Currency.PLN),
                new BankAccount("Lloyds", "Alice", "Savings", Currency.PLN),
                Amount.of(10.20)
        );



    }

}