package com.ivanbochko.moneytransfer.transfer.service;

import com.ivanbochko.moneytransfer.account.model.BankAccount;
import com.ivanbochko.moneytransfer.common.model.Amount;
import com.ivanbochko.moneytransfer.common.model.Currency;
import com.ivanbochko.moneytransfer.transfer.TransferProcessor;
import com.ivanbochko.moneytransfer.transfer.model.Transfer;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

public class RoutingTransferProcessorTest {
    private TransferProcessor innerTransferProcessor = mock(TransferProcessor.class);
    private TransferProcessor interbankTransferProcessor = mock(TransferProcessor.class);
    private RoutingTransferProcessor routingTransferProcessor =
            new RoutingTransferProcessor(innerTransferProcessor, interbankTransferProcessor);

    @Test
    public void shouldCallIntraBankForSameBanks() {
        Transfer transfer = new Transfer(
                bankAccount("Barklays", "John"),
                bankAccount("Barklays", "Alice"),
                Amount.of(10.20)
        );

        routingTransferProcessor.process(transfer);

        verify(innerTransferProcessor).process(transfer);
        verifyZeroInteractions(interbankTransferProcessor);
    }

    @Test
    public void shouldCallInterBankForSupportedBanks() {
        Transfer transfer = new Transfer(
                bankAccount("Barklays", "John"),
                bankAccount("Lloyds", "Alice"),
                Amount.of(10.20)
        );

        routingTransferProcessor.process(transfer);

        verify(interbankTransferProcessor).process(transfer);
        verifyZeroInteractions(innerTransferProcessor);
    }

    private BankAccount bankAccount(String bankName, String name) {
        return new BankAccount(bankName, name, "Savings", Currency.GBP);
    }
}