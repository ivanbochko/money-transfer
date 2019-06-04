package com.ivanbochko.moneytransfer.fx;

import com.ivanbochko.moneytransfer.common.model.Amount;
import com.ivanbochko.moneytransfer.common.model.Currency;
import com.ivanbochko.moneytransfer.common.model.Money;

public interface CurrencyConverter {

    Amount convert(Money money, Currency targetCurrency);
}
