package com.ivanbochko.moneytransfer.resources.transfer;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ivanbochko.moneytransfer.account.model.BankAccount;
import com.ivanbochko.moneytransfer.common.model.Currency;
import com.ivanbochko.moneytransfer.transfer.model.Transfer;
import com.ivanbochko.moneytransfer.transfer.model.TransferRecord;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class TransferView {
    private String senderCustomer;
    private String senderAccount;
    private Currency senderCurrency;

    private String recipientBank;
    private String recipientCustomer;
    private String recipientAccount;
    private Currency recipientCurrency;

    private Double amountInSenderCurrency;
    private Double amountInRecipientCurrency;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime issuedUtc;

    TransferView(TransferRecord transferRecord) {
        Transfer transfer = transferRecord.getTransfer();
        BankAccount sender = transfer.getSender();
        BankAccount recipient = transfer.getRecipient();

        senderCustomer = sender.getCustomer();
        senderAccount = sender.getAccount();
        senderCurrency = sender.getCurrency();

        recipientBank = recipient.getBank();
        recipientCustomer = recipient.getCustomer();
        recipientAccount = recipient.getAccount();
        recipientCurrency = recipient.getCurrency();

        amountInSenderCurrency = transfer.getAmount().toDouble();
        amountInRecipientCurrency = transferRecord.getTargetAmount().toDouble();

        issuedUtc = transferRecord.getIssuedUtc();
    }
}
