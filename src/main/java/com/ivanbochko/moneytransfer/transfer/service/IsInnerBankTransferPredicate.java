package com.ivanbochko.moneytransfer.transfer.service;

import com.ivanbochko.moneytransfer.account.model.BankAccount;
import com.ivanbochko.moneytransfer.transfer.model.Transfer;

import java.util.function.Predicate;

public class IsInnerBankTransferPredicate implements Predicate<Transfer> {

    @Override
    public boolean test(Transfer transfer) {
        BankAccount sender = transfer.getSender();
        BankAccount recipient = transfer.getRecipient();
        return sender.getBank().equals(recipient.getBank());
    }
}
