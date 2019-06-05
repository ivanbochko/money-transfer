package com.ivanbochko.moneytransfer.fx.service;

import com.ivanbochko.moneytransfer.common.model.Amount;
import com.ivanbochko.moneytransfer.common.model.Currency;
import com.ivanbochko.moneytransfer.common.model.Money;
import com.ivanbochko.moneytransfer.fx.CurrencyConverter;
import com.ivanbochko.moneytransfer.fx.FxRatesProvider;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class FxRatesCurrencyConverter implements CurrencyConverter {
    private final FxRatesProvider fxRatesProvider;

    @Inject
    FxRatesCurrencyConverter(FxRatesProvider fxRatesProvider) {
        this.fxRatesProvider = fxRatesProvider;
    }

    @Override
    public Amount convert(Money money, Currency targetCurrency) {
        if (money.getAmount().isZero() || money.getCurrency() == targetCurrency) {
            return money.getAmount();
        }

        double fxRateFrom = fxRatesProvider.getFxRate(money.getCurrency());
        double fxRateTo = fxRatesProvider.getFxRate(targetCurrency);

        return money.getAmount().multiplyTo(fxRateFrom / fxRateTo);
    }
}
