package com.ivanbochko.moneytransfer.transfer.service;

import com.ivanbochko.moneytransfer.account.model.BankAccount;
import com.ivanbochko.moneytransfer.common.model.Amount;
import com.ivanbochko.moneytransfer.common.model.Currency;
import com.ivanbochko.moneytransfer.transfer.model.Transfer;
import com.ivanbochko.moneytransfer.transfer.model.TransferRecord;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class InMemoryTransferStorageTest {
    private InMemoryTransferStorage inMemoryTransferStorage = new InMemoryTransferStorage();

    @Test
    public void shouldStoreTransfers() {
        Transfer transfer = new Transfer(new BankAccount("A", "B", "C", Currency.USD),
                new BankAccount("X", "Y", "Z", Currency.USD),
                Amount.of(10000.0));
        TransferRecord transferRecord = new TransferRecord(transfer, Amount.of(100), LocalDateTime.now());
        inMemoryTransferStorage.store(transferRecord);

        assertThat(inMemoryTransferStorage.getStoreSize()).isEqualTo(1);
        assertThat(inMemoryTransferStorage.getAllTransfers()).containsExactly(transferRecord);
    }
}