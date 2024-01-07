package com.eteration.simplebanking.controller;

import org.springframework.http.HttpStatus;

public class TransactionStatus {
    private Enum<HttpStatus> status;
    private String approvelCode;

    public TransactionStatus(Enum<HttpStatus> status, String approvelCode) {
        this.status = status;
        this.approvelCode = approvelCode;
    }

    public Enum<HttpStatus> getStatus() {
        return status;
    }

    public void setStatus(Enum<HttpStatus> status) {
        this.status = status;
    }

    public String getApprovelCode() {
        return approvelCode;
    }

    public void setApprovelCode(String approvelCode) {
        this.approvelCode = approvelCode;
    }
}
