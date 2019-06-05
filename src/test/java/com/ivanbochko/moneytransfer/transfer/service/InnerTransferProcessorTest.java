package com.ivanbochko.moneytransfer.transfer.service;

import com.ivanbochko.moneytransfer.account.model.BankAccount;
import com.ivanbochko.moneytransfer.common.model.Amount;
import com.ivanbochko.moneytransfer.common.model.Currency;
import com.ivanbochko.moneytransfer.transfer.TransferResult;
import com.ivanbochko.moneytransfer.transfer.model.Transfer;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class InnerTransferProcessorTest {
    private final SuccessfulTransferCommitter transferCommitter = mock(SuccessfulTransferCommitter.class);
    private InnerTransferProcessor innerTransferProcessor = new InnerTransferProcessor(transferCommitter);

    @Test
    public void shouldAssumeInnerTransfersAreAlwaysSuccessful() {
        Transfer transfer = new Transfer(new BankAccount("A", "B", "C", Currency.USD),
                new BankAccount("X", "Y", "Z", Currency.USD),
                Amount.of(10000.0));
        TransferResult transferResult = innerTransferProcessor.process(transfer);

        assertThat(transferResult.isSuccessful());
        verify(transferCommitter).commitSuccessful(transfer);
    }

}