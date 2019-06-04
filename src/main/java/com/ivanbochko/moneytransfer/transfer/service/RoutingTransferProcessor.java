package com.ivanbochko.moneytransfer.transfer.service;

import com.ivanbochko.moneytransfer.transfer.TransferProcessor;
import com.ivanbochko.moneytransfer.transfer.TransferResult;
import com.ivanbochko.moneytransfer.transfer.model.Transfer;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

@Singleton
public class RoutingTransferProcessor implements TransferProcessor {
    private final TransferProcessor innerTransferProcessor;
    private final TransferProcessor interbankTransferProcessor;
    private final IsInnerBankTransferPredicate isInnerBankTransferPredicate = new IsInnerBankTransferPredicate();

    @Inject
    public RoutingTransferProcessor(@Named("intrabank") TransferProcessor innerTransferProcessor,
                                    @Named("interbank") TransferProcessor interbankTransferProcessor) {
        this.innerTransferProcessor = innerTransferProcessor;
        this.interbankTransferProcessor = interbankTransferProcessor;
    }

    @Override
    public TransferResult process(Transfer transfer) {
        if (isInnerBankTransferPredicate.test(transfer)) {
            return innerTransferProcessor.process(transfer);
        } else {
            return interbankTransferProcessor.process(transfer);
        }
    }
}
