package com.ivanbochko.moneytransfer.fx;

import com.ivanbochko.moneytransfer.common.model.Currency;

/**
 * Provides FX rate against base currency (USD)
 */
public interface FxRatesProvider {

    double getFxRate(Currency currency);
}
