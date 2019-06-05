package com.ivanbochko.moneytransfer.transfer.service;

import com.ivanbochko.moneytransfer.account.model.BankAccount;
import com.ivanbochko.moneytransfer.common.model.Amount;
import com.ivanbochko.moneytransfer.common.model.Currency;
import com.ivanbochko.moneytransfer.transfer.ExternalBankTransferSender;
import com.ivanbochko.moneytransfer.transfer.TransferResult;
import com.ivanbochko.moneytransfer.transfer.model.Transfer;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

public class InterbankTransferProcessorTest {
    private final SuccessfulTransferCommitter transferCommitter = mock(SuccessfulTransferCommitter.class);
    private final ExternalBankTransferSender externalBankTransferSender = mock(ExternalBankTransferSender.class);
    private InterbankTransferProcessor innerTransferProcessor =
            new InterbankTransferProcessor(transferCommitter, externalBankTransferSender);

    @Test
    public void shouldMakeSuccessfulTransferIfBankSupported() {
        Transfer transfer = new Transfer(new BankAccount("Barklays", "B", "C", Currency.USD),
                new BankAccount("Lloyds", "Y", "Z", Currency.USD),
                Amount.of(10000.0));

        TransferResult transferResult = innerTransferProcessor.process(transfer);

        assertThat(transferResult.isSuccessful()).isTrue();

        verify(externalBankTransferSender).sendMoneyToTargetReceiver(transfer);
        verify(transferCommitter).commitSuccessful(transfer);
    }

    @Test
    public void shouldFailIfBankIsNotSupported() {
        Transfer transfer = new Transfer(new BankAccount("Barklays", "B", "C", Currency.USD),
                new BankAccount("Alior", "Y", "Z", Currency.USD),
                Amount.of(10000.0));

        doThrow(new ExternalBankTransferSender.FailedExternalTransactionException("Bank 'Alior' is not supported."))
                .when(externalBankTransferSender).sendMoneyToTargetReceiver(transfer);

        TransferResult transferResult = innerTransferProcessor.process(transfer);

        assertThat(transferResult.isSuccessful()).isFalse();

        verify(externalBankTransferSender).sendMoneyToTargetReceiver(transfer);
        verifyZeroInteractions(transferCommitter);
    }
}