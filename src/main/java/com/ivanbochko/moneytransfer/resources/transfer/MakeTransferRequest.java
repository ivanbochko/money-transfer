package com.ivanbochko.moneytransfer.resources.transfer;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class MakeTransferRequest {
    @Valid
    @NotNull
    private TransferParty sender;

    @Valid
    @NotNull
    private TransferParty recipient;

    @NotNull
    private Double amount;
}
