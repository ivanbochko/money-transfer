package com.ivanbochko.moneytransfer.common;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.ivanbochko.moneytransfer.account.AccountModule;
import com.ivanbochko.moneytransfer.account.model.BankAccount;
import com.ivanbochko.moneytransfer.fx.FxModule;
import com.ivanbochko.moneytransfer.resources.account.CreateBankAccountRequest;
import com.ivanbochko.moneytransfer.transfer.TransferModule;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

public class MainModule extends AbstractModule {
    private final MoneyTransferAppConfig config;

    public MainModule(MoneyTransferAppConfig config) {
        this.config = config;
    }

    @Override
    protected void configure() {
        bind(MoneyTransferAppConfig.class).toInstance(config);
        bind(String.class).annotatedWith(Names.named("bankIdentifier"))
                .toInstance(config.getBankIdentifier());

        CreateBankAccountRequest systemBankAccountRequest = config.getSystemBankAccount();
        bind(CreateBankAccountRequest.class).annotatedWith(Names.named("systemBankAccountRequest"))
                .toInstance(systemBankAccountRequest);

        BankAccount systemBankAccount = new BankAccount(config.getBankIdentifier(),
                systemBankAccountRequest.getCustomer(),
                systemBankAccountRequest.getAccount(),
                systemBankAccountRequest.getCurrency());
        bind(BankAccount.class).annotatedWith(Names.named("systemBankAccount"))
                .toInstance(systemBankAccount);

        initClock();

        install(new FxModule());
        install(new AccountModule());
        install(new TransferModule());
    }

    private void initClock() {
        if (config.getFixedTime() != null) {
            Clock fixedClock = Clock.fixed(Instant.parse(config.getFixedTime()), ZoneOffset.UTC);
            bind(Clock.class).toInstance(fixedClock);
        } else {
            bind(Clock.class).toInstance(Clock.systemUTC());
        }
    }
}
