package com.ivanbochko.moneytransfer.resources.transfer;

import com.ivanbochko.moneytransfer.account.model.BankAccount;
import com.ivanbochko.moneytransfer.common.model.Currency;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class TransferParty {
    @NotBlank
    private String bank;
    @NotBlank
    private String customer;
    @NotBlank
    private String account;
    @NotNull
    private Currency currency;

    public BankAccount asBankAccount() {
        return new BankAccount(bank, customer, account, currency);
    }
}
