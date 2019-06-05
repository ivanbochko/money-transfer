package com.ivanbochko.moneytransfer.common;

import com.ivanbochko.moneytransfer.common.model.Currency;
import com.ivanbochko.moneytransfer.resources.account.CreateBankAccountRequest;
import io.dropwizard.Configuration;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Data
@NoArgsConstructor
public class MoneyTransferAppConfig extends Configuration {
    @NotBlank
    private String bankIdentifier;

    @Valid
    @NotNull
    private CreateBankAccountRequest systemBankAccount;

    private Map<Currency, Double> fxRates;

    /**
     * test purpose configuration.
     * Fixed time. e.g.: 2019-06-04T10:15:30.00Z
     */
    private String fixedTime;
}
