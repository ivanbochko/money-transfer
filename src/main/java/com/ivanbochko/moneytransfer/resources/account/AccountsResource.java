package com.ivanbochko.moneytransfer.resources.account;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ivanbochko.moneytransfer.account.AllBankAccountsReader;
import com.ivanbochko.moneytransfer.account.BankAccountCreator;
import com.ivanbochko.moneytransfer.account.model.BankAccount;
import com.ivanbochko.moneytransfer.common.model.Money;
import com.ivanbochko.moneytransfer.transfer.BalanceReader;
import io.swagger.annotations.Api;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Api
@Path("/accounts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
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
    public BankAccountView createBankAccount(@Valid @NotNull CreateBankAccountRequest account) {
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
