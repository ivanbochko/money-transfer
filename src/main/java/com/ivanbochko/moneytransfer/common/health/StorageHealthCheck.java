package com.ivanbochko.moneytransfer.common.health;

import com.codahale.metrics.health.HealthCheck;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ivanbochko.moneytransfer.transfer.TransferStorage;

@Singleton
public class StorageHealthCheck extends HealthCheck {
    private static final int THRESHOLD = 1000;
    private final TransferStorage transferStorage;

    @Inject
    public StorageHealthCheck(TransferStorage transferStorage) {
        this.transferStorage = transferStorage;
    }

    protected Result check() {
        if (transferStorage.getStoreSize() > THRESHOLD) {
            return Result.unhealthy("Too big transfer storage.");
        }
        return Result.healthy();
    }
}
