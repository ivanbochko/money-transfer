package com.ivanbochko.moneytransfer;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.ivanbochko.moneytransfer.common.MainModule;
import com.ivanbochko.moneytransfer.common.MoneyTransferAppConfig;
import com.ivanbochko.moneytransfer.common.health.StorageHealthCheck;
import io.dropwizard.Application;
import io.dropwizard.configuration.ResourceConfigurationSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class MoneyTransferApp extends Application<MoneyTransferAppConfig> {
    private static final String STORAGE_CHECK = "storageCheck";
    private static final String SERVER = "server";
    private static final String CONFIG_YAML = "/config.yaml";

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            new MoneyTransferApp().run(SERVER, CONFIG_YAML);
        } else {
            new MoneyTransferApp().run(args);
        }
    }

    @Override
    public void initialize(Bootstrap<MoneyTransferAppConfig> bootstrap) {
        bootstrap.setConfigurationSourceProvider(new ResourceConfigurationSourceProvider());
    }

    public void run(MoneyTransferAppConfig config, Environment environment) {
        MainModule module = new MainModule(config);
        Injector injector = Guice.createInjector(module);

        environment.healthChecks().register(STORAGE_CHECK, injector.getInstance(StorageHealthCheck.class));
    }
}
