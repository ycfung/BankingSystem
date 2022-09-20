package com.rcf.banking.exception;

import com.rcf.banking.util.Currency;

public class AccountAlreadyExistsException extends Exception {
    public AccountAlreadyExistsException(String message) {
        super(message);
    }

    public AccountAlreadyExistsException() {
        super("This account already exists");
    }


}
