package com.ivanbochko.moneytransfer.transfer.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ivanbochko.moneytransfer.account.model.BankAccount;
import com.ivanbochko.moneytransfer.common.model.Amount;
import com.ivanbochko.moneytransfer.common.model.Money;
import com.ivanbochko.moneytransfer.fx.CurrencyConverter;
import com.ivanbochko.moneytransfer.transfer.OnSuccessfulTransferHandler;
import com.ivanbochko.moneytransfer.transfer.TransferStorage;
import com.ivanbochko.moneytransfer.transfer.model.Transfer;
import com.ivanbochko.moneytransfer.transfer.model.TransferRecord;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

@Singleton
public class SuccessfulTransferCommitter {
    private final TransferStorage transferStorage;
    private final CurrencyConverter currencyConverter;
    private final Clock clock;
    private final List<OnSuccessfulTransferHandler> handlers;

    @Inject
    SuccessfulTransferCommitter(TransferStorage transferStorage,
                                CurrencyConverter currencyConverter,
                                Clock clock,
                                List<OnSuccessfulTransferHandler> handlers) {
        this.transferStorage = transferStorage;
        this.currencyConverter = currencyConverter;
        this.clock = clock;
        this.handlers = handlers;
    }

    public void commitSuccessful(Transfer transfer) {
        BankAccount sender = transfer.getSender();
        BankAccount recipient = transfer.getRecipient();
        Money money = new Money(transfer.getAmount(), sender.getCurrency());
        Amount targetAmount = currencyConverter.convert(money, recipient.getCurrency());

        TransferRecord transferRecord = TransferRecord.builder()
                .transfer(transfer)
                .targetAmount(targetAmount)
                .issuedUtc(LocalDateTime.now(clock))
                .build();
        transferStorage.store(transferRecord);
        notifyObservers(transferRecord);
    }

    private void notifyObservers(TransferRecord transferRecord) {
        handlers.forEach(each -> each.onSuccessfulTransfer(transferRecord));
    }
}
