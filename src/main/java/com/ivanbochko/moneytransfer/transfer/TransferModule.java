package com.ivanbochko.moneytransfer.transfer;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Names;
import com.ivanbochko.moneytransfer.account.model.BankAccount;
import com.ivanbochko.moneytransfer.transfer.service.BalanceCheckingProcessorDecorator;
import com.ivanbochko.moneytransfer.transfer.service.InMemoryTransferStorage;
import com.ivanbochko.moneytransfer.transfer.service.InnerTransferProcessor;
import com.ivanbochko.moneytransfer.transfer.service.InterbankTransferProcessor;
import com.ivanbochko.moneytransfer.transfer.service.LloydsCommercialAccountSender;
import com.ivanbochko.moneytransfer.transfer.service.ObservingBalanceReader;
import com.ivanbochko.moneytransfer.transfer.service.RoutingTransferProcessor;
import com.ivanbochko.moneytransfer.transfer.service.SuccessfulTransferCommitter;
import com.ivanbochko.moneytransfer.transfer.service.TransferStorageReader;

import javax.inject.Named;
import java.util.Collections;
import java.util.List;

public class TransferModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(BalanceReader.class).to(ObservingBalanceReader.class);
        bind(OnSuccessfulTransferHandler.class)
                .annotatedWith(Names.named("balanceActuator"))
                .to(ObservingBalanceReader.class);
        bind(TransferProcessor.class).to(RoutingTransferProcessor.class);
        bind(ExternalBankTransferSender.class).to(LloydsCommercialAccountSender.class);
        bind(TransferStorage.class).to(InMemoryTransferStorage.class);
        bind(TransfersReader.class).to(TransferStorageReader.class);
    }

    @Provides
    @Named("intrabank")
    TransferProcessor intrabankProcessor(BalanceReader balanceReader,
                                         @Named("systemBankAccount") BankAccount systemBankAccount,
                                         SuccessfulTransferCommitter transferCommitter) {
        return new BalanceCheckingProcessorDecorator(
                new InnerTransferProcessor(transferCommitter), systemBankAccount, balanceReader);
    }

    @Provides
    @Named("interbank")
    TransferProcessor interbankProcessor(BalanceReader balanceReader,
                                         @Named("systemBankAccount") BankAccount systemBankAccount,
                                         SuccessfulTransferCommitter transferCommitter,
                                         ExternalBankTransferSender externalBankTransferSender) {
        return new BalanceCheckingProcessorDecorator(
                new InterbankTransferProcessor(transferCommitter,
                        externalBankTransferSender), systemBankAccount, balanceReader);
    }

    @Provides
    List<OnSuccessfulTransferHandler> getObservers(@Named("balanceActuator") OnSuccessfulTransferHandler handler) {
        return Collections.singletonList(handler);
    }
}
