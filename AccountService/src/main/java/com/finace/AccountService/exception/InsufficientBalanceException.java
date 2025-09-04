package com.finace.AccountService.exception;

public class InsufficientBalanceException  extends  RuntimeException{
    public InsufficientBalanceException(String message) {
        super(message);
    }
}
