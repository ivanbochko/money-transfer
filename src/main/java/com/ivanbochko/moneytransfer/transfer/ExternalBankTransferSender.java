package com.ivanbochko.moneytransfer.transfer;

import com.ivanbochko.moneytransfer.transfer.model.Transfer;

public interface ExternalBankTransferSender {

    void sendMoneyToTargetReceiver(Transfer transfer);


    class FailedExternalTransactionException extends RuntimeException {
        public FailedExternalTransactionException(String message) {
            super(message);
        }
    }
}
