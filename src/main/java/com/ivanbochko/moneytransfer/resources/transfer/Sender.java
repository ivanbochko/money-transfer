package com.ivanbochko.moneytransfer.resources.transfer;

import com.ivanbochko.moneytransfer.account.model.BankAccount;
import com.ivanbochko.moneytransfer.common.model.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sender {
    @NotBlank
    private String customer;
    @NotBlank
    private String account;
    @NotNull
    private Currency currency;

    public BankAccount asBankAccount(String homeBank) {
        return new BankAccount(homeBank, customer, account, currency);
    }
}
