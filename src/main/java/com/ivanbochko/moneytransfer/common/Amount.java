package com.ivanbochko.moneytransfer.common;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Dedicated to hold money amount in integer representation.
 */
public final class Amount {
    private final BigDecimal amount;

    private Amount(BigDecimal amount) {
        this.amount = amount.setScale(5, RoundingMode.HALF_UP);
    }

    public static Amount of(double decimalNumber) {
        return new Amount(new BigDecimal(decimalNumber));
    }

    public double toDouble() {
        return amount.doubleValue();
    }

    public boolean isNegative() {
        return amount.doubleValue() < 0;
    }

    public boolean isZero() {
        return amount.equals(BigDecimal.ZERO);
    }

    public Amount multiplyTo(double value) {
        BigDecimal bigDecimal = new BigDecimal(value);
        return new Amount(amount.multiply(bigDecimal));
    }
}
