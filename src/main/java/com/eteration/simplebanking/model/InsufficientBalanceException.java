package com.eteration.simplebanking.model;

public class InsufficientBalanceException extends Exception {
    public InsufficientBalanceException(String exceptionValue) {
        super(exceptionValue);
    }
}
