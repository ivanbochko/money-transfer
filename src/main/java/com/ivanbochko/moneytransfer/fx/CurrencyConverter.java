package com.ivanbochko.moneytransfer.fx;

import com.ivanbochko.moneytransfer.common.Amount;
import com.ivanbochko.moneytransfer.common.Currency;
import com.ivanbochko.moneytransfer.common.Money;

public interface CurrencyConverter {

    Amount convert(Money money, Currency targetCurrency);
}
