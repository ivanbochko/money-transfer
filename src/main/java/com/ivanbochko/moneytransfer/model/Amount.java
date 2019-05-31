package com.ivanbochko.moneytransfer.model;

/**
 * Dedicated to hold money amount in integer representation,
 * considering predefined 5 digits after point.
 */
public final class Amount {
    private static final double PRECISION = 100000.0;
    private final long amount;

    public Amount(long amount) {
        this.amount = amount;
    }

    public static Amount of(double decimalNumber) {
        return new Amount((long) (decimalNumber * PRECISION));
    }

    public double asDouble() {
        return amount / PRECISION;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Amount amount1 = (Amount) o;

        return amount == amount1.amount;
    }

    @Override
    public int hashCode() {
        return (int) (amount ^ (amount >>> 32));
    }

    @Override
    public String toString() {
        return "Amount{" +
                "amount=" + amount +
                '}';
    }
}
