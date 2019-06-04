package com.ivanbochko.moneytransfer.resources.transfer;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ivanbochko.moneytransfer.account.model.BankAccount;
import com.ivanbochko.moneytransfer.common.model.Amount;
import com.ivanbochko.moneytransfer.transfer.TransferProcessor;
import com.ivanbochko.moneytransfer.transfer.TransferResult;
import com.ivanbochko.moneytransfer.transfer.TransfersReader;
import com.ivanbochko.moneytransfer.transfer.model.Transfer;
import com.ivanbochko.moneytransfer.transfer.model.TransferRecord;

import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/transfers")
@Singleton
public class TransfersResource {
    private static final int UNPROCESSABLE = 422;
    private final TransferProcessor transferProcessor;
    private final TransfersReader transfersReader;

    @Inject
    public TransfersResource(TransferProcessor transferProcessor, TransfersReader transfersReader) {
        this.transferProcessor = transferProcessor;
        this.transfersReader = transfersReader;
    }

    @POST
    public Response makeTransfer(@Valid MakeTransferRequest transferRequest) {
        BankAccount sender = transferRequest.getSender().asBankAccount();
        BankAccount recipient = transferRequest.getRecipient().asBankAccount();

        Transfer transfer = new Transfer(sender, recipient, Amount.of(transferRequest.getAmount()));
        TransferResult transferResult = transferProcessor.process(transfer);
        if (transferResult.isSuccessful()) {
            return Response.ok().build();
        } else {
            return Response.status(UNPROCESSABLE)
                    .entity(transferResult.getErrors())
                    .build();
        }
    }


    @GET
    public List<TransferRecord> getAllTransfers() {
        return transfersReader.getAllTransfers();
    }
}
