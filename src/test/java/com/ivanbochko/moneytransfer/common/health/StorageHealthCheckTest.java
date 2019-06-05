package com.ivanbochko.moneytransfer.common.health;

import com.ivanbochko.moneytransfer.transfer.TransferStorage;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class StorageHealthCheckTest {
    private TransferStorage transferStorage = mock(TransferStorage.class);
    private StorageHealthCheck storageHealthCheck = new StorageHealthCheck(transferStorage);

    @Test
    public void shouldBeHealthyBeforeSizeThreshold() {
        given(transferStorage.getStoreSize()).willReturn(StorageHealthCheck.THRESHOLD);

        assertThat(storageHealthCheck.check().isHealthy()).isTrue();
    }

    @Test
    public void shouldBeUnhealthyAfterSizeThreshold() {
        given(transferStorage.getStoreSize()).willReturn(StorageHealthCheck.THRESHOLD + 1);

        assertThat(storageHealthCheck.check().isHealthy()).isFalse();
        assertThat(storageHealthCheck.check().getMessage())
                .isEqualTo(StorageHealthCheck.TOO_BIG_TRANSFER_STORAGE);
    }

}