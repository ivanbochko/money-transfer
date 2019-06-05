package com.ivanbochko.moneytransfer.transfer.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ivanbochko.moneytransfer.transfer.TransferStorage;
import com.ivanbochko.moneytransfer.transfer.TransfersReader;
import com.ivanbochko.moneytransfer.transfer.model.TransferRecord;

import java.util.List;

@Singleton
public class TransferStorageReader implements TransfersReader {
    private final TransferStorage transferStorage;

    @Inject
    public TransferStorageReader(TransferStorage transferStorage) {
        this.transferStorage = transferStorage;
    }

    @Override
    public List<TransferRecord> getAllTransfers() {
        return transferStorage.getAllTransfers();
    }
}
