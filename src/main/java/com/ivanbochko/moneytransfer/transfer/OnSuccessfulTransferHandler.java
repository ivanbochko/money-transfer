package com.ivanbochko.moneytransfer.transfer;

import com.ivanbochko.moneytransfer.transfer.model.TransferRecord;

public interface OnSuccessfulTransferHandler {

    void onSuccessfulTransfer(TransferRecord transferRecord);
}
