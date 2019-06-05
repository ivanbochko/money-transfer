package com.ivanbochko.moneytransfer.integration;

import com.ivanbochko.moneytransfer.MoneyTransferApp;
import com.ivanbochko.moneytransfer.common.MoneyTransferAppConfig;
import io.dropwizard.testing.ConfigOverride;
import io.dropwizard.testing.junit.DropwizardAppRule;

import java.net.ServerSocket;

public class AppRule extends DropwizardAppRule<MoneyTransferAppConfig> {

    public AppRule() {
        super(MoneyTransferApp.class, "config.yaml",
                ConfigOverride.config("server.connector.port", findFreePort()),
                ConfigOverride.config("fixedTime", "2019-06-04T10:15:30.00Z"));
    }

    public String getLocalAddress() {
        return "http://127.0.0.1:" + getLocalPort() + "/money-transfer";
    }

    private static String findFreePort() {
        try (ServerSocket socket = new ServerSocket(0)) {
            return String.valueOf(socket.getLocalPort());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
