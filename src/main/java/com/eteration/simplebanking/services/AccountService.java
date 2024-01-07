package com.eteration.simplebanking.services;


import com.eteration.simplebanking.controller.TransactionStatus;
import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.model.InsufficientBalanceException;
import com.eteration.simplebanking.model.Transaction;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;

@Service
public class AccountService {

    private final List<Account> accounts;

    public AccountService(List<Account> accounts) {
        this.accounts = accounts;
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public Account findAccount(String accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }

    public TransactionStatus depositProcessTransaction(String accountNumber, Transaction transaction) throws InsufficientBalanceException, NoSuchAlgorithmException {
        Account account = findAccount(accountNumber);
        if (Objects.nonNull(account)) {
            transaction.transactionApply(account);
            return transactionStatusOk(account.getTransactions().get(0).getApprovalCode());
        } else {
            return transactionStatusFail(account.getTransactions().get(0).getApprovalCode());
        }
    }

    public TransactionStatus transactionStatusOk(String approvalCode) {
        return new TransactionStatus(HttpStatus.OK, approvalCode);
    }

    public TransactionStatus transactionStatusFail(String approvalCode) {
        return new TransactionStatus(HttpStatus.BAD_REQUEST, approvalCode);
    }

    public void addAccount(String owner, String accountNumber) {
        addAccount(new Account(owner,accountNumber));
    }
}
