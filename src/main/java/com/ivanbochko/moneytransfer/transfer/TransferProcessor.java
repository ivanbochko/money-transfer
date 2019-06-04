package com.ivanbochko.moneytransfer.transfer;

import com.ivanbochko.moneytransfer.transfer.model.Transfer;

public interface TransferProcessor {

    TransferResult process(Transfer transfer);
}
