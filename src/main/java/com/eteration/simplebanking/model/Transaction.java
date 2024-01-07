package com.eteration.simplebanking.model;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Objects;

public abstract class Transaction {
    private Double amount;
    private LocalDateTime date;
    private String type;
    private String approvalCode;

    public abstract void transactionApply(Account account) throws InsufficientBalanceException, NoSuchAlgorithmException;

    public Transaction(Double amount) {
        this.amount = amount;
        this.date = LocalDateTime.now();
    }

    public Transaction() {
        this.date = LocalDateTime.now();
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public double getAmount() {
        if (Objects.isNull(amount)) {
            return 0.0;
        }
        return amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getApprovalCode() throws NoSuchAlgorithmException {
        return MessageDigest.getInstance("MD5").digest((this.amount.toString() + this.date).getBytes()).toString();
    }
}
