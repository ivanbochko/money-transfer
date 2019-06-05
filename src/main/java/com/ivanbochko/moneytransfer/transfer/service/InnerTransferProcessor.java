package com.ivanbochko.moneytransfer.transfer.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ivanbochko.moneytransfer.transfer.TransferProcessor;
import com.ivanbochko.moneytransfer.transfer.TransferResult;
import com.ivanbochko.moneytransfer.transfer.model.Transfer;

@Singleton
public class InnerTransferProcessor implements TransferProcessor {
    private final SuccessfulTransferCommitter transferCommitter;

    @Inject
    public InnerTransferProcessor(SuccessfulTransferCommitter transferCommitter) {
        this.transferCommitter = transferCommitter;
    }

    @Override
    public TransferResult process(Transfer transfer) {
        transferCommitter.commitSuccessful(transfer);
        return TransferResult.success();
    }
}
