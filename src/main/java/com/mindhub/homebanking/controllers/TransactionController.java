package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.CardApplicationDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.services.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    private ClientService clientService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private TransferService transferService;

    @Transactional
    @PostMapping("/transactions")
    public ResponseEntity<Object> transaction(@RequestParam Double amount,
                                              @RequestParam String description,
                                              @RequestParam String numberAccountOrigin,
                                              @RequestParam String numberAccountDestiny,
                                              Authentication authentication  ){

        Client client = clientService.findByEmail(authentication.getName());//cliente autenticado

        //Check if the fields are empty
        if (amount.isNaN() || description.isBlank() || numberAccountOrigin.isBlank() || numberAccountDestiny.isBlank()) {
            return new ResponseEntity<>("has unfilled fields", HttpStatus.FORBIDDEN);
        }

        Account accountOrigin = accountService.findByNumber(numberAccountOrigin);//cuenta origen
        Account accountDestiny = accountService.findByNumber(numberAccountDestiny);//cuenta destino

        //Verifies that the account number of both origin and destination exists in our database
        if (accountService.findByNumber(numberAccountOrigin) == null || accountService.findByNumber(numberAccountDestiny) == null) {
            return new ResponseEntity<>("One or both account numbers do not exist in our database", HttpStatus.FORBIDDEN);
        }
        //Verify that the account numbers are different
        if (numberAccountOrigin.equals(numberAccountDestiny)) {
            return new ResponseEntity<>("The account numbers must be different", HttpStatus.FORBIDDEN);
        }
        //verifies that the source account has funds
        if (accountOrigin.getBalance() < amount && accountOrigin.getBalance() > 0){
            return new ResponseEntity<>("your account does not have enough funds to make the transfer", HttpStatus.FORBIDDEN);
        }
        //verifico que el monto sea mayor a 0
        if (amount <= 0) {
            return new ResponseEntity<>("The transfer amount must be greater than 0", HttpStatus.FORBIDDEN);
        }
        //Verify that the source account belongs to the authenticated client
        if (!accountOrigin.getClient().equals(client)){
            return new ResponseEntity<>("The source account does not belong to the authenticated client", HttpStatus.FORBIDDEN);
        }

        Transaction transactionCredit = new Transaction(TransactionType.CREDIT, amount, description, LocalDateTime.now(), accountOrigin.getBalance());
        Transaction transactionDebit = new Transaction(TransactionType.DEBIT, amount, description, LocalDateTime.now(), accountDestiny.getBalance());

        accountOrigin.addTransaction(transactionCredit);
        accountDestiny.addTransaction(transactionDebit);

        transferService.saveTransfer(transactionCredit);
        transferService.saveTransfer(transactionDebit);

        Double credit = accountOrigin.getBalance() - amount;
        Double debit = accountDestiny.getBalance() + amount;

        accountOrigin.setBalance(credit);
        accountDestiny.setBalance(debit);

        accountService.saveAccount(accountOrigin);
        accountService.saveAccount(accountDestiny);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Transactional
    @PostMapping("/transaction/app")
    public ResponseEntity<Object> creationPayments(@RequestBody CardApplicationDTO cardDTO,
                                                   Account account,
                                                   Authentication authentication){

        Client client = clientService.findByEmail(authentication.getName());

        if(client.getEmail() != null){
            return new ResponseEntity<>("One or both account numbers do not exist in our database", HttpStatus.UNAUTHORIZED);
        }

        if(cardDTO.getCvv() > 0 || cardDTO.getAmount().isNaN() || cardDTO.getDescription().isBlank() || cardDTO.getNumber().isBlank()){
            return new ResponseEntity<>("has unfilled fields", HttpStatus.FORBIDDEN);
        }

        if(cardDTO.getAmount() > account.getBalance()){
            return new ResponseEntity<>("you do not have sufficient funds", HttpStatus.FORBIDDEN);
        }


        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
