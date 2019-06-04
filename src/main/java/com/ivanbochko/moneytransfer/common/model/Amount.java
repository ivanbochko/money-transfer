package com.ivanbochko.moneytransfer.common.model;

import lombok.Value;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Dedicated to hold money amount in integer representation.
 */
@Value
public final class Amount {
    private final BigDecimal value;

    private Amount(BigDecimal value) {
        this.value = value.setScale(5, RoundingMode.HALF_UP);
    }

    public static Amount of(double decimalNumber) {
        return new Amount(new BigDecimal(decimalNumber));
    }

    public double toDouble() {
        return value.doubleValue();
    }

    public boolean isNegative() {
        return value.doubleValue() < 0;
    }

    public boolean isZero() {
        return value.equals(BigDecimal.ZERO);
    }

    public Amount multiplyTo(double value) {
        BigDecimal bigDecimal = new BigDecimal(value);
        return new Amount(this.value.multiply(bigDecimal));
    }

    public boolean lessThan(Amount amount) {
        return value.compareTo(amount.value) < 0;
    }

    public Amount minus(Amount amount) {
        return new Amount(this.value.subtract(amount.value));
    }

    public Amount plus(Amount amount) {
        return new Amount(this.value.add(amount.value));
    }
}
