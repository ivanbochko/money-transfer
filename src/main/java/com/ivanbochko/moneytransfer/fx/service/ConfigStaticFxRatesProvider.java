package com.ivanbochko.moneytransfer.fx.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ivanbochko.moneytransfer.common.model.Currency;
import com.ivanbochko.moneytransfer.fx.FxRatesProvider;

import java.util.Map;

@Singleton
public class ConfigStaticFxRatesProvider implements FxRatesProvider {
    private final Map<Currency, Double> currencyToUsdPerUnit;

    @Inject
    public ConfigStaticFxRatesProvider(Map<Currency, Double> currencyToUsdPerUnit) {
        this.currencyToUsdPerUnit = currencyToUsdPerUnit;
    }

    @Override
    public double getFxRate(Currency currency) {
        return currencyToUsdPerUnit.get(currency);
    }
}
