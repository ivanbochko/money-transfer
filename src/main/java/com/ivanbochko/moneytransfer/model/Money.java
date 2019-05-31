package com.ivanbochko.moneytransfer.model;

public final class Money {
    private final Amount amount;
    private final Currency currency;

    public Money(Amount amount, Currency currency) {
        if (amount == null || currency == null) {
            throw new IllegalArgumentException("Money cannot be created with null values.");
        }
        this.amount = amount;
        this.currency = currency;
    }

    public Amount getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }
}
