package com.ivanbochko.moneytransfer.transfer.model;

import com.ivanbochko.moneytransfer.common.model.Amount;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Builder
@Value
public class TransferRecord {
    private Transfer transfer;
    private Amount targetAmount;
    private LocalDateTime issuedUtc;
}
