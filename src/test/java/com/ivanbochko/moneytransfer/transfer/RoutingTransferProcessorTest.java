package com.ivanbochko.moneytransfer.transfer;

import com.ivanbochko.moneytransfer.account.BankAccount;
import com.ivanbochko.moneytransfer.common.Amount;
import com.ivanbochko.moneytransfer.common.Currency;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RoutingTransferProcessorTest {
    @Mock
    private InnerTransferProcessor innerTransferProcessor;

    @Mock
    private InterbankTransferProcessor interbankTransferProcessor;

    @InjectMocks
    private RoutingTransferProcessor routingTransferProcessor;

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