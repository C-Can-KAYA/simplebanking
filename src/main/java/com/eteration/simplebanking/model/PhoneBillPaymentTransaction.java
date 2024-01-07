package com.eteration.simplebanking.model;

import java.security.NoSuchAlgorithmException;

public class PhoneBillPaymentTransaction extends Transaction {
    private String operatorName;
    private String phoneNumber;
    private Double amount;

    public PhoneBillPaymentTransaction(String operatorName, String phoneNumber, Double amount) throws NoSuchAlgorithmException {
        super(amount);
    }

    @Override
    public void transactionApply(Account account) throws InsufficientBalanceException, NoSuchAlgorithmException {
        PhoneBillPaymentTransaction phoneBillPaymentTransaction = new PhoneBillPaymentTransaction(this.operatorName, this.phoneNumber, getAmount());
        phoneBillPaymentTransaction.setType("PhoneBillPaymentTransaction");
        account.getTransactions().add(phoneBillPaymentTransaction);
        account.withdraw(getAmount());
    }
}
