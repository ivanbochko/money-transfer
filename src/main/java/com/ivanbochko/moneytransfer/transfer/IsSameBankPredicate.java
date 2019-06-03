package com.ivanbochko.moneytransfer.transfer;

import com.ivanbochko.moneytransfer.account.BankAccount;

import java.util.function.Predicate;

public class IsSameBankPredicate implements Predicate<BankAccount> {
    private BankAccount sender;

    public IsSameBankPredicate(BankAccount sender) {
        this.sender = sender;
    }

    @Override
    public boolean test(BankAccount recipient) {
        return sender.getBank().equals(recipient.getBank());
    }
}
