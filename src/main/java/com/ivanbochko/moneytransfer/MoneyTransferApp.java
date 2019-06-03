package com.ivanbochko.moneytransfer;

import com.ivanbochko.moneytransfer.common.MoneyTransferAppConfig;
import com.ivanbochko.moneytransfer.common.health.StorageHealthCheck;
import io.dropwizard.Application;
import io.dropwizard.configuration.ResourceConfigurationSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MoneyTransferApp extends Application<MoneyTransferAppConfig> {
    private static final Logger logger = LoggerFactory.getLogger(MoneyTransferApp.class);

    public static void main(String[] args) throws Exception {
        new MoneyTransferApp().run("server", "/config.yaml");
    }

    @Override
    public void initialize(Bootstrap<MoneyTransferAppConfig> bootstrap) {
        bootstrap.setConfigurationSourceProvider(new ResourceConfigurationSourceProvider());
    }

    public void run(MoneyTransferAppConfig config, Environment environment) {
        environment.healthChecks().register("storageCheck", new StorageHealthCheck());
    }
}
