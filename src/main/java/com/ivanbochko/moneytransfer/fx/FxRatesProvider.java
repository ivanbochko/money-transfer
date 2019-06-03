package com.ivanbochko.moneytransfer.fx;

import com.ivanbochko.moneytransfer.common.Currency;

/**
 * Provides FX rate against base currency (USD)
 */
public interface FxRatesProvider {

    double getFxRate(Currency currency);
}
