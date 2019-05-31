package com.ivanbochko.moneytransfer.application;

import java.util.List;

public interface TransferResult {
    boolean isSuccessful();

    List<String> getErrors();

    class SuccessTransferResult implements TransferResult {
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

        public FailedTransferResult(List<String> errors) {
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
