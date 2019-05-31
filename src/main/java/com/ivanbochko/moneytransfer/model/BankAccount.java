package com.ivanbochko.moneytransfer.model;

public final class BankAccount {
    private final String bank;
    private final String customer;
    private final String account;
    private final Currency currency;

    public BankAccount(String bank, String customer, String account, Currency currency) {
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
