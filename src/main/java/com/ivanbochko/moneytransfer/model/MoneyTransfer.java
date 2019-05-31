package com.ivanbochko.moneytransfer.model;

public final class MoneyTransfer {
    private final BankAccount sender;
    private final BankAccount recipient;
    private final Amount amount;

    public MoneyTransfer(BankAccount sender, BankAccount recipient, Amount amount) {
        this.sender = sender;
        this.recipient = recipient;
        this.amount = amount;
    }

    public BankAccount getSender() {
        return sender;
    }

    public BankAccount getRecipient() {
        return recipient;
    }

    public Amount getAmount() {
        return amount;
    }
}
