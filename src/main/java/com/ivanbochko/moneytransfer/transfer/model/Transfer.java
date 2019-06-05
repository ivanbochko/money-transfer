package com.ivanbochko.moneytransfer.transfer.model;

import com.ivanbochko.moneytransfer.account.model.BankAccount;
import com.ivanbochko.moneytransfer.common.model.Amount;
import lombok.Value;

@Value
public final class Transfer {
    private final BankAccount sender;
    private final BankAccount recipient;
    private final Amount amount;

    public Transfer(BankAccount sender, BankAccount recipient, Amount amount) {
        assertArguments(sender, recipient, amount);
        this.sender = sender;
        this.recipient = recipient;
        this.amount = amount;
    }

    private void assertArguments(BankAccount sender, BankAccount recipient, Amount amount) {
        if (sender == null || recipient == null
                || amount == null || amount.isNegative() || amount.isZero()) {
            throw new IllegalArgumentException("Cannot construct a transfer.");
        }
        if (sender.equals(recipient)) {
            throw new IllegalArgumentException("Sender cannot be the same as recipient.");
        }
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
