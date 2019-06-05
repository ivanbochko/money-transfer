package com.ivanbochko.moneytransfer.resources.account;

import com.ivanbochko.moneytransfer.common.model.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateBankAccountRequest {
    @NotBlank
    private String customer;
    @NotBlank
    private String account;
    @NotNull
    private Currency currency;
}
