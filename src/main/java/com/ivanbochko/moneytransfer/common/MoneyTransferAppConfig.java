package com.ivanbochko.moneytransfer.common;

import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotBlank;

public class MoneyTransferAppConfig extends Configuration {
    @NotBlank
    private String bankIdeantifier;
}
