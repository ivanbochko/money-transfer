package com.ivanbochko.moneytransfer.application;

import com.ivanbochko.moneytransfer.model.MoneyTransfer;

public interface TransferService {
    TransferResult make(MoneyTransfer transfer);
}
