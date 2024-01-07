package com.eteration.simplebanking.controller;

import com.eteration.simplebanking.model.*;
import com.eteration.simplebanking.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping(path = "account/v1/")
public class AccountController {
    @Autowired
    AccountService service;

    @GetMapping(path = "{accountNumber}")
    public ResponseEntity<Account> getAccount(@PathVariable String accountNumber) {
        Account account = service.findAccount(accountNumber);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @PostMapping(path = "credit/{accountNumber}")
    public ResponseEntity<TransactionStatus> credit(@PathVariable String accountNumber, @RequestBody DepositTransaction transaction) throws InsufficientBalanceException, NoSuchAlgorithmException {
        TransactionStatus status = service.depositProcessTransaction(accountNumber, transaction);
        return new ResponseEntity<>(status, (HttpStatus) status.getStatus());
    }

    @PostMapping(path = "debit/{accountNumber}")
    public ResponseEntity<TransactionStatus> debit(@PathVariable String accountNumber, @RequestBody WithdrawalTransaction transaction) throws InsufficientBalanceException, NoSuchAlgorithmException {
        TransactionStatus status = service.depositProcessTransaction(accountNumber, transaction);
        return new ResponseEntity<>(status, (HttpStatus) status.getStatus());
    }
}