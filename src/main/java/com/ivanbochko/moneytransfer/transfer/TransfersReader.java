package com.ivanbochko.moneytransfer.transfer;

import com.ivanbochko.moneytransfer.transfer.model.TransferRecord;

import java.util.List;

public interface TransfersReader {

    List<TransferRecord> getAllTransfers();
}
