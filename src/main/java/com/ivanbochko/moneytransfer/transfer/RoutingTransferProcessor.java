package com.ivanbochko.moneytransfer.transfer;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class RoutingTransferProcessor implements TransferProcessor {
    private final InnerTransferProcessor innerTransferProcessor;
    private final InterbankTransferProcessor interbankTransferProcessor;

    @Inject
    public RoutingTransferProcessor(InnerTransferProcessor innerTransferProcessor,
                                    InterbankTransferProcessor interbankTransferProcessor) {
        this.innerTransferProcessor = innerTransferProcessor;
        this.interbankTransferProcessor = interbankTransferProcessor;
    }

    @Override
    public TransferResult process(Transfer transfer) {
        IsSameBankPredicate isSameBankPredicate = new IsSameBankPredicate(transfer.getSender());
        if (isSameBankPredicate.test(transfer.getRecipient())) {
            return innerTransferProcessor.process(transfer);
        } else {
            return interbankTransferProcessor.process(transfer);
        }
    }
}
