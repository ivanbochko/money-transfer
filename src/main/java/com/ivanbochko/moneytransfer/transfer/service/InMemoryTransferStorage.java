package com.ivanbochko.moneytransfer.transfer.service;

import com.google.inject.Singleton;
import com.ivanbochko.moneytransfer.transfer.TransferStorage;
import com.ivanbochko.moneytransfer.transfer.model.TransferRecord;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Singleton
public class InMemoryTransferStorage implements TransferStorage {
    private final List<TransferRecord> transfers = Collections.synchronizedList(new ArrayList<>());

    @Override
    public void store(TransferRecord transfer) {
        transfers.add(transfer);
    }

    @Override
    public Integer getStoreSize() {
        return transfers.size();
    }

    @Override
    public List<TransferRecord> getAllTransfers() {
        return transfers;
    }
}
