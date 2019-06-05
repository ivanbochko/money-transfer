package com.ivanbochko.moneytransfer.transfer.model;

import com.ivanbochko.moneytransfer.common.model.Amount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
public class TransferRecord {
    private Transfer transfer;
    private Amount targetAmount;
    private LocalDateTime issuedUtc;
}
