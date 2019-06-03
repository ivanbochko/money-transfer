package com.ivanbochko.moneytransfer.transfer;

public interface TransferProcessor {

    TransferResult process(Transfer transfer);
}
