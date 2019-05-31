package com.ivanbochko.moneytransfer.model;


/**
 * Subset of currencies from ISO 4217
 */
public enum Currency {
    EUR("Euro"),
    GBP("United Kingdom Pound"),
    USD("United States Dollar"),
    PLN("Polish Zloty");

    private final String description;

    Currency(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}