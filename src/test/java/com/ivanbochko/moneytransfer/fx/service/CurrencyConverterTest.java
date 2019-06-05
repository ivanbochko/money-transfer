package com.ivanbochko.moneytransfer.fx.service;

import com.ivanbochko.moneytransfer.common.model.Amount;
import com.ivanbochko.moneytransfer.common.model.Currency;
import com.ivanbochko.moneytransfer.common.model.Money;
import com.ivanbochko.moneytransfer.fx.CurrencyConverter;
import com.ivanbochko.moneytransfer.fx.FxRatesProvider;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class CurrencyConverterTest {

    private CurrencyConverter currencyConverter;

    @Before
    public void setUp() {
        FxRatesProvider fxRatesProvider = mock(FxRatesProvider.class);
        given(fxRatesProvider.getFxRate(Currency.USD))
                .willReturn(1.0);
        given(fxRatesProvider.getFxRate(Currency.EUR))
                .willReturn(1.1);
        given(fxRatesProvider.getFxRate(Currency.GBP))
                .willReturn(1.25);
        currencyConverter = new FxRatesCurrencyConverter(fxRatesProvider);
    }

    @Test
    public void shouldReturnSameAmountForIdenticalCurrencies() {
        Money money = new Money(Amount.of(10.34), Currency.USD);

        Amount converted = currencyConverter.convert(money, Currency.USD);

        assertThat(converted.toDouble()).isEqualTo(10.34);
    }

    @Test
    public void shouldConvertFromBaseCurrency() {
        Money money = new Money(Amount.of(10.00), Currency.USD);

        Amount converted = currencyConverter.convert(money, Currency.EUR);

        assertThat(converted.toDouble()).isEqualTo(9.09091);
    }

    @Test
    public void shouldConvertToBaseCurrency() {
        Money money = new Money(Amount.of(10.00), Currency.EUR);

        Amount converted = currencyConverter.convert(money, Currency.USD);

        assertThat(converted.toDouble()).isEqualTo(11.0);
    }

    @Test
    public void shouldConvertBetweenNonBaseCurrencies() {
        Money money = new Money(Amount.of(10.00), Currency.EUR);

        Amount converted = currencyConverter.convert(money, Currency.GBP);

        assertThat(converted.toDouble()).isEqualTo(8.8);
    }
}