package com.eteration.simplebanking.model;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
public class WithdrawalTransaction extends Transaction {

    public WithdrawalTransaction(double amount) {
        super(amount);
    }

    public WithdrawalTransaction() {
        super();
    }

    @Override
    public void transactionApply(Account account) throws InsufficientBalanceException {
        WithdrawalTransaction withdrawalTransaction = new WithdrawalTransaction(getAmount());
        withdrawalTransaction.setType("WithdrawalTransaction");
        withdrawalTransaction.setDate(LocalDateTime.now());
        account.getTransactions().add(withdrawalTransaction);
        account.withdraw(getAmount());
    }
}


