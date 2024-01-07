package com.eteration.simplebanking.model;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class DepositTransaction extends Transaction {

    public DepositTransaction(double amount) {
        super(amount);
    }

    public DepositTransaction() {
        super();
    }

    @Override
    public void transactionApply(Account account) throws InsufficientBalanceException {
        DepositTransaction depositTransaction = new DepositTransaction(getAmount());
        depositTransaction.setType("DepositTransaction");
        depositTransaction.setDate(LocalDateTime.now());
        account.getTransactions().add(depositTransaction);
        account.deposit(getAmount());
    }
}
