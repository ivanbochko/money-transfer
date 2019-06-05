package com.ivanbochko.moneytransfer.transfer;

import com.ivanbochko.moneytransfer.transfer.model.TransferRecord;

import java.util.List;

public interface TransferStorage {

    void store(TransferRecord transfer);

    Integer getStoreSize();

    List<TransferRecord> getAllTransfers();
}
