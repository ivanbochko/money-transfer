package com.ivanbochko.moneytransfer.transfer.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ivanbochko.moneytransfer.account.model.BankAccount;
import com.ivanbochko.moneytransfer.common.model.Amount;
import com.ivanbochko.moneytransfer.transfer.ExternalBankTransferSender;
import com.ivanbochko.moneytransfer.transfer.model.Transfer;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Named;

@Slf4j
@Singleton
public class LloydsCommercialAccountSender implements ExternalBankTransferSender {

    private static final String SUPPORTED_BANK = "Lloyds";
    private final String hostBankIdentifier;

    @Inject
    public LloydsCommercialAccountSender(@Named("bankIdentifier") String hostBankIdentifier) {
        this.hostBankIdentifier = hostBankIdentifier;
    }


    @Override
    public void sendMoneyToTargetReceiver(Transfer transfer) {
        BankAccount sender = transfer.getSender();
        BankAccount recipient = transfer.getRecipient();
        Amount amount = transfer.getAmount();
        if (SUPPORTED_BANK.equals(recipient.getBank())) {
            log.info("Sending {} {} from {} to commercial account in {} with conversion to {}.",
                    amount.toDouble(), sender.getCurrency(), hostBankIdentifier, SUPPORTED_BANK, recipient.getCurrency());
        } else {
            throw new FailedExternalTransactionException("Bank '" + recipient.getBank() + "' is not supported.");
        }
    }

}
