package com.ivanbochko.moneytransfer.common.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AmountTest {
    @Test
    public void shouldCreateAmountFromDecimalWith5DigitsPrecision() {
        Amount amount = Amount.of(1587.9944123);

        assertThat(amount.toDouble()).isEqualTo(1587.99441);
    }

    @Test
    public void shouldRepresentItselfAsDouble() {
        double value = 1587.99441;
        Amount amount = Amount.of(value);

        assertThat(amount.toDouble()).isEqualTo(value);
    }
}