package com.ivanbochko.moneytransfer.account;

import com.ivanbochko.moneytransfer.account.model.BankAccount;

import java.util.List;

public interface AllBankAccountsReader {

    List<BankAccount> getAllAccounts();
}
