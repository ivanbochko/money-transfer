package com.ivanbochko.moneytransfer.account;

import com.ivanbochko.moneytransfer.common.Currency;

public final class BankAccount {
    private final String bank;
    private final String customer;
    private final String account;
    private final Currency currency;

    public BankAccount(String bank, String customer, String account, Currency currency) {
        if (bank == null || customer == null || account == null || currency == null) {
            throw new IllegalArgumentException("Cannot construct bank account.");
        }
        this.bank = bank;
        this.customer = customer;
        this.account = account;
        this.currency = currency;
    }

    public String getBank() {
        return bank;
    }

    public String getCustomer() {
        return customer;
    }

    public String getAccount() {
        return account;
    }

    public Currency getCurrency() {
        return currency;
    }
}
