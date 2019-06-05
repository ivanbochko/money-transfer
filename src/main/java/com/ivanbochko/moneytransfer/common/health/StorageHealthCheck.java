package com.ivanbochko.moneytransfer.common.health;

import com.codahale.metrics.health.HealthCheck;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ivanbochko.moneytransfer.transfer.TransferStorage;

@Singleton
public class StorageHealthCheck extends HealthCheck {
    static final int THRESHOLD = 1000;
    static final String TOO_BIG_TRANSFER_STORAGE = "Too big transfer storage.";
    private final TransferStorage transferStorage;

    @Inject
    public StorageHealthCheck(TransferStorage transferStorage) {
        this.transferStorage = transferStorage;
    }

    protected Result check() {
        if (transferStorage.getStoreSize() > THRESHOLD) {
            return Result.unhealthy(TOO_BIG_TRANSFER_STORAGE);
        }
        return Result.healthy();
    }
}
