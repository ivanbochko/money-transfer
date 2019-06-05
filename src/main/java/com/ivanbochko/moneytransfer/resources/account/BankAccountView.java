package com.ivanbochko.moneytransfer.resources.account;

import com.ivanbochko.moneytransfer.account.model.BankAccount;
import com.ivanbochko.moneytransfer.common.model.Currency;
import com.ivanbochko.moneytransfer.common.model.Money;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountView {
    private String bank;
    private String customer;
    private String account;
    private Currency currency;
    private Double balance;

    BankAccountView(BankAccount bankAccount, Money balance) {
        this.bank = bankAccount.getBank();
        this.customer = bankAccount.getCustomer();
        this.account = bankAccount.getAccount();
        this.currency = bankAccount.getCurrency();
        this.balance = balance.getAmount().toDouble();
    }
}
