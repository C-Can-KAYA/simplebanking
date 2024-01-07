package com.eteration.simplebanking;

import com.eteration.simplebanking.controller.AccountController;
import com.eteration.simplebanking.controller.TransactionStatus;
import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.model.DepositTransaction;
import com.eteration.simplebanking.model.InsufficientBalanceException;
import com.eteration.simplebanking.model.WithdrawalTransaction;
import com.eteration.simplebanking.services.AccountService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@ContextConfiguration
@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)
class ControllerTests  {

    @Spy
    @InjectMocks
    private AccountController controller;

    @Mock
    private AccountService service;

    @Test
    public void givenId_Credit_thenReturnJson()
    throws Exception {
        Account account = new Account("Kerem Karaca", "17892");
        TransactionStatus status = new TransactionStatus(HttpStatus.OK,"İşlem Başarıyla gerçekleşti");
        when(service.depositProcessTransaction(anyString(),any(DepositTransaction.class))).thenReturn(status);
        doReturn(account).when(service).findAccount( "17892");
        ResponseEntity<TransactionStatus> result = controller.credit( "17892", new DepositTransaction(1000.0));
        verify(service, times(1)).depositProcessTransaction(anyString(),any(DepositTransaction.class));
        assertEquals(status.getStatus(), result.getBody().getStatus());
    }

    @Test
    public void givenId_CreditAndThenDebit_thenReturnJson()
    throws Exception {

        Account account = new Account("Kerem Karaca", "17892");
        service.addAccount(account);
        TransactionStatus status = new TransactionStatus(HttpStatus.OK,"İşlem Başarıyla gerçekleşti");
        when(service.depositProcessTransaction(anyString(),any(DepositTransaction.class))).thenReturn(status);
        when(service.depositProcessTransaction(anyString(),any(WithdrawalTransaction.class))).thenReturn(status);
        ResponseEntity<TransactionStatus> result = controller.credit( "17892", new DepositTransaction(1000.0));
        account.post(new DepositTransaction(1000.0));
        ResponseEntity<TransactionStatus> result2 = controller.debit( "17892", new WithdrawalTransaction(50.0));
        account.post(new WithdrawalTransaction(50.0));
        verify(service, times(1)).depositProcessTransaction(anyString(),any(WithdrawalTransaction.class));
        verify(service, times(1)).depositProcessTransaction(anyString(),any(DepositTransaction.class));
        assertEquals(status.getStatus(), result.getBody().getStatus());
        assertEquals(status.getStatus(), result2.getBody().getStatus());
        assertEquals(950.0, account.getBalance(),0.001);
    }

    @Test
    public void givenId_CreditAndThenDebitMoreGetException_thenReturnJson()
    throws Exception {
        Assertions.assertThrows( InsufficientBalanceException.class, () -> {
            Account account = new Account("Kerem Karaca", "17892");
            TransactionStatus status = new TransactionStatus(HttpStatus.OK,"İşlem Başarıyla gerçekleşti");
            when(service.depositProcessTransaction(anyString(),any(DepositTransaction.class))).thenReturn(status);
            when(service.depositProcessTransaction(anyString(),any(WithdrawalTransaction.class))).thenReturn(status);
            ResponseEntity<TransactionStatus> result = controller.credit( "17892", new DepositTransaction(1000.0));
            account.post(new DepositTransaction(1000.0));
            assertEquals(status.getStatus(), result.getBody().getStatus());
            assertEquals(1000.0, account.getBalance(),0.001);
            verify(service, times(1)).depositProcessTransaction(anyString(),any(DepositTransaction.class));
            when(service.depositProcessTransaction(anyString(),any(WithdrawalTransaction.class))).thenReturn(status);
            ResponseEntity<TransactionStatus> result2 = controller.debit( "17892", new WithdrawalTransaction(5000.0));
            verify(service, times(1)).depositProcessTransaction(anyString(),any(WithdrawalTransaction.class));
            account.post(new WithdrawalTransaction(5000.0));
        });
    }

    @Test
    public void givenId_GetAccount_thenReturnJson()
    throws Exception {

        Account account = new Account("Kerem Karaca", "17892");

        doReturn(account).when(service).findAccount( "17892");
        ResponseEntity<Account> result = controller.getAccount( "17892");
        verify(service, times(1)).findAccount("17892");
        assertEquals(account, result.getBody());
    }

}
