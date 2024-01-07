package com.eteration.simplebanking.model;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Account {
    private String accountNumber;
    private String owner;
    private Double balance;
    private LocalDateTime createDate;
    private List<Transaction> transactions;

    public Account(String owner, String accountNumber) {
        this.owner = owner;
        this.accountNumber = accountNumber;
        this.balance = 0.0;
        this.transactions = new ArrayList<>();
        this.createDate = LocalDateTime.now();
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public Double getBalance() {
        return balance;
    }

    public void withdraw(double amount) throws InsufficientBalanceException {
        if (amount > 0) {
            if (amount <= this.balance) {
                this.balance = balance - amount;
            } else {
                throw new InsufficientBalanceException("Yeterli miktarda para bulunamadığı için işlem gerçekleştirilemedi");
            }
        } else {
            throw new IllegalArgumentException("Çekilen miktar sıfırdan büyük olmalıdır.");
        }
    }

    public void deposit(double amount) throws InsufficientBalanceException {
        if (amount > 0) {
            this.balance = balance + amount;
        } else {
            throw new InsufficientBalanceException("Çekilen miktar sıfırdan büyük olmalıdır.");
        }
    }

    public void post(Transaction transaction) throws InsufficientBalanceException, NoSuchAlgorithmException {
        transaction.transactionApply(this);
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }
}
