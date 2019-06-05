package com.ivanbochko.moneytransfer.transfer.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ivanbochko.moneytransfer.account.model.BankAccount;
import com.ivanbochko.moneytransfer.common.model.Money;
import com.ivanbochko.moneytransfer.transfer.BalanceReader;
import com.ivanbochko.moneytransfer.transfer.TransferProcessor;
import com.ivanbochko.moneytransfer.transfer.TransferResult;
import com.ivanbochko.moneytransfer.transfer.model.Transfer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
public class BalanceCheckingProcessorDecorator implements TransferProcessor {
    private final TransferProcessor decorated;
    private final BankAccount systemBankAccount;
    private final BalanceReader balanceReader;

    @Inject
    public BalanceCheckingProcessorDecorator(TransferProcessor decorated,
                                             BankAccount systemBankAccount,
                                             BalanceReader balanceReader) {
        this.decorated = decorated;
        this.systemBankAccount = systemBankAccount;
        this.balanceReader = balanceReader;
    }


    @Override
    public TransferResult process(Transfer transfer) {
        BankAccount sender = transfer.getSender();
        Money balance = balanceReader.getBalance(sender);
        if (!systemBankAccount.equals(sender) && balance.getAmount().lessThan(transfer.getAmount())) {
            log.info("Not enough money on {} to perform transfer.", sender.getAccount());
            return TransferResult.failure("Not enough money on the account.");
        }
        return decorated.process(transfer);
    }
}
