package com.ivanbochko.moneytransfer.resources.transfer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MakeTransferRequest {
    @Valid
    @NotNull
    private Sender sender;

    @Valid
    @NotNull
    private Recipient recipient;

    @NotNull
    private Double amount;
}
