package com.ivanbochko.moneytransfer.transfer;

import java.util.List;

import static java.util.Arrays.asList;

public interface TransferResult {
    static TransferResult failure(String... errorMessages) {
        return new FailedTransferResult(asList(errorMessages));
    }

    static TransferResult success() {
        return new SuccessTransferResult();
    }

    boolean isSuccessful();

    List<String> getErrors();

    class SuccessTransferResult implements TransferResult {
        private SuccessTransferResult() {
        }

        @Override
        public boolean isSuccessful() {
            return true;
        }

        @Override
        public List<String> getErrors() {
            return null;
        }
    }

    class FailedTransferResult implements TransferResult {
        private final List<String> errors;

        private FailedTransferResult(List<String> errors) {
            this.errors = errors;
        }

        @Override
        public boolean isSuccessful() {
            return false;
        }

        @Override
        public List<String> getErrors() {
            return errors;
        }
    }
}
