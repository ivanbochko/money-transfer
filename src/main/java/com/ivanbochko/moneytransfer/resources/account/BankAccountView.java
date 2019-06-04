package com.ivanbochko.moneytransfer.resources.account;

import com.ivanbochko.moneytransfer.account.model.BankAccount;
import com.ivanbochko.moneytransfer.common.model.Money;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BankAccountView {
    private final BankAccount bankAccount;
    private final Money balance;

    public BankAccountView(BankAccount bankAccount, Money balance) {
        this.bankAccount = bankAccount;
        this.balance = balance;
    }
}
