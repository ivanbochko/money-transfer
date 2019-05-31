package com.ivanbochko.moneytransfer.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AmountTest {
    @Test
    public void shouldCreateAmountFromFloatingPoint() {
        Amount amount = Amount.of(1587.9944123);

        assertThat(amount).isEqualTo(new Amount(158799441));
    }

    @Test
    public void shouldRepresentItselfAsDouble() {
        double value = 1587.99441;
        Amount amount = Amount.of(value);

        assertThat(amount.asDouble()).isEqualTo(value);
    }
}