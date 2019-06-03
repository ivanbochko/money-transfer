package com.ivanbochko.moneytransfer.common.health;

import com.codahale.metrics.health.HealthCheck;

public class StorageHealthCheck extends HealthCheck {
    protected Result check() {
        return Result.healthy();
    }
}
