package com.ivanbochko.moneytransfer.account;

import com.ivanbochko.moneytransfer.account.model.BankAccount;

import java.util.List;

public interface AccountStorage {

    BankAccount create(BankAccount account);

    List<BankAccount> getAll();
}
