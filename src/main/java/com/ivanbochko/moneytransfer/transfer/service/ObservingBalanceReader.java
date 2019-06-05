package com.ivanbochko.moneytransfer.transfer.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ivanbochko.moneytransfer.account.model.BankAccount;
import com.ivanbochko.moneytransfer.common.model.Amount;
import com.ivanbochko.moneytransfer.common.model.Money;
import com.ivanbochko.moneytransfer.transfer.BalanceReader;
import com.ivanbochko.moneytransfer.transfer.OnSuccessfulTransferHandler;
import com.ivanbochko.moneytransfer.transfer.model.Transfer;
import com.ivanbochko.moneytransfer.transfer.model.TransferRecord;

import javax.inject.Named;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class ObservingBalanceReader implements BalanceReader, OnSuccessfulTransferHandler {
    private final Map<BankAccount, Amount> accountToBalance = new HashMap<>();
    private final String hostBankIdentifier;

    @Inject
    public ObservingBalanceReader(@Named("bankIdentifier") String hostBankIdentifier) {
        this.hostBankIdentifier = hostBankIdentifier;
    }

    @Override
    public Money getBalance(BankAccount account) {
        Amount balance = accountToBalance.getOrDefault(account, Amount.of(0));
        return new Money(balance, account.getCurrency());
    }

    @Override
    public void onSuccessfulTransfer(TransferRecord transferRecord) {
        Transfer transfer = transferRecord.getTransfer();
        debitSender(transfer);
        if (hostBankIdentifier.equals(transfer.getRecipient().getBank())) {
            creditRecipient(transferRecord);
        }
    }

    private void creditRecipient(TransferRecord transferRecord) {
        BankAccount recipient = transferRecord.getTransfer().getRecipient();
        Amount balance = accountToBalance.getOrDefault(recipient, Amount.of(0));
        Amount debited = balance.plus(transferRecord.getTargetAmount());
        accountToBalance.put(recipient, debited);
    }

    private void debitSender(Transfer transfer) {
        BankAccount sender = transfer.getSender();
        Amount balance = accountToBalance.getOrDefault(sender, Amount.of(0));
        Amount debited = balance.minus(transfer.getAmount());
        accountToBalance.put(sender, debited);
    }
}
