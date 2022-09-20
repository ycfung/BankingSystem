package com.rcf.banking.exception;

public class NotEnoughBalanceException extends Exception {
    public NotEnoughBalanceException() {
        super("The user does not have enough balance to perform this operation");
    }

    public NotEnoughBalanceException(String message) {
        super(message);
    }

}
