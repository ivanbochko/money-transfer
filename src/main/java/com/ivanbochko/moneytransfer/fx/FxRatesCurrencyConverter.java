package com.ivanbochko.moneytransfer.fx;

import com.ivanbochko.moneytransfer.common.Amount;
import com.ivanbochko.moneytransfer.common.Currency;
import com.ivanbochko.moneytransfer.common.Money;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Map;

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
