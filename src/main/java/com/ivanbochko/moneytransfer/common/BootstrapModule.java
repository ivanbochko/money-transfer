package com.ivanbochko.moneytransfer.common;

import com.google.inject.AbstractModule;
import io.dropwizard.setup.Environment;

public class BootstrapModule extends AbstractModule {
    private final MoneyTransferAppConfig config;
    private final Environment environment;

    public BootstrapModule(MoneyTransferAppConfig config, Environment environment) {
        this.config = config;
        this.environment = environment;
    }

    @Override
    protected void configure() {
        bind(MoneyTransferAppConfig.class).toInstance(config);
        bind(Environment.class).toInstance(environment);
    }
}
