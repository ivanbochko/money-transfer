package com.ivanbochko.moneytransfer.resources.account;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ivanbochko.moneytransfer.account.AllBankAccountsReader;
import com.ivanbochko.moneytransfer.account.BankAccountCreator;
import com.ivanbochko.moneytransfer.account.model.BankAccount;
import com.ivanbochko.moneytransfer.common.model.Money;
import com.ivanbochko.moneytransfer.transfer.BalanceReader;

import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.util.List;
import java.util.stream.Collectors;

@Path("/accounts")
@Singleton
public class AccountsResource {
    private final BankAccountCreator bankAccountCreator;
    private final BalanceReader balanceReader;
    private final AllBankAccountsReader allBankAccountsReader;

    @Inject
    public AccountsResource(BankAccountCreator bankAccountCreator, BalanceReader balanceReader, AllBankAccountsReader allBankAccountsReader) {
        this.bankAccountCreator = bankAccountCreator;
        this.balanceReader = balanceReader;
        this.allBankAccountsReader = allBankAccountsReader;
    }

    @POST
    public BankAccountView createBankAccount(@Valid CreateBankAccountRequest account) {
        BankAccount bankAccount = bankAccountCreator.create(account.getCustomer(), account.getAccount(), account.getCurrency());
        Money balance = balanceReader.getBalance(bankAccount);
        return new BankAccountView(bankAccount, balance);
    }

    @GET
    public List<BankAccountView> getAllBankAccounts() {
        return allBankAccountsReader.getAllAccounts().stream()
                .map(account -> new BankAccountView(account, balanceReader.getBalance(account)))
                .collect(Collectors.toList());
    }
}
