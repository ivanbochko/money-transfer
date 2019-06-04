package com.ivanbochko.moneytransfer.transfer.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ivanbochko.moneytransfer.transfer.ExternalBankTransferSender;
import com.ivanbochko.moneytransfer.transfer.ExternalBankTransferSender.FailedExternalTransactionException;
import com.ivanbochko.moneytransfer.transfer.TransferProcessor;
import com.ivanbochko.moneytransfer.transfer.TransferResult;
import com.ivanbochko.moneytransfer.transfer.model.Transfer;
import lombok.extern.slf4j.Slf4j;

@Singleton
@Slf4j
public class InterbankTransferProcessor implements TransferProcessor {
    private final SuccessfulTransferCommitter transferCommitter;
    private final ExternalBankTransferSender externalBankTransferSender;

    @Inject
    public InterbankTransferProcessor(SuccessfulTransferCommitter transferCommitter,
                                      ExternalBankTransferSender externalBankTransferSender) {
        this.transferCommitter = transferCommitter;
        this.externalBankTransferSender = externalBankTransferSender;
    }

    @Override
    public TransferResult process(Transfer transfer) {
        try {
            externalBankTransferSender.sendMoneyToTargetReceiver(transfer);
            transferCommitter.commitSuccessful(transfer);
            return TransferResult.success();
        } catch (FailedExternalTransactionException ex) {
            log.error("Transaction failed.", ex);
            return TransferResult.failure("Cannot perform interbank transfer.");
        }
    }
}
