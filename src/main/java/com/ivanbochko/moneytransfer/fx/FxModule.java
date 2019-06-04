package com.ivanbochko.moneytransfer.fx;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.ivanbochko.moneytransfer.common.model.Currency;
import com.ivanbochko.moneytransfer.common.MoneyTransferAppConfig;
import com.ivanbochko.moneytransfer.fx.service.ConfigStaticFxRatesProvider;
import com.ivanbochko.moneytransfer.fx.service.FxRatesCurrencyConverter;

import java.util.Map;

public class FxModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(FxRatesProvider.class).to(ConfigStaticFxRatesProvider.class);
        bind(CurrencyConverter.class).to(FxRatesCurrencyConverter.class);
    }

    @Provides
    Map<Currency, Double> getFxRates(MoneyTransferAppConfig config) {
        return config.getFxRates();
    }
}
