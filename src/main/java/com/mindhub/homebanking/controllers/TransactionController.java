package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    @Transactional
    @PostMapping("/transactions")
    public ResponseEntity<Object> transaction(@RequestParam Double amount,
                                              @RequestParam String description,
                                              @RequestParam String numberAccountOrigin,
                                              @RequestParam String numberAccountDestiny,
                                              Authentication authentication  ){

        Client client = clientRepository.findByEmail(authentication.getName());//cliente autenticado

        Account accountOrigin = accountRepository.findByAccountNumber(numberAccountOrigin);//cuenta origen
        Account accountDestiny = accountRepository.findByAccountNumber(numberAccountDestiny);//cuenta destino

        //Check if the fields are empty
        if (amount.isNaN() || description.isBlank() || numberAccountOrigin.isBlank() || numberAccountDestiny.isBlank()) {
            return new ResponseEntity<>("has unfilled fields", HttpStatus.FORBIDDEN);
        }
        //Verifies that the account number of both origin and destination exists in our database
        if (accountRepository.findByAccountNumber(numberAccountOrigin) == null || accountRepository.findByAccountNumber(numberAccountDestiny) == null) {
            return new ResponseEntity<>("One or both account numbers do not exist in our database", HttpStatus.FORBIDDEN);
        }
        //Verify that the account numbers are different
        if (!numberAccountOrigin.equals(numberAccountDestiny)) {
            return new ResponseEntity<>("The account numbers must be different", HttpStatus.FORBIDDEN);
        }
        //verifies that the source account has funds
        if (accountOrigin.getBalance() < amount){
            return new ResponseEntity<>("your account does not have enough funds to make the transfer", HttpStatus.FORBIDDEN);
        }
        //Verify that the source account belongs to the authenticated client
        if (!accountOrigin.getClient().equals(client)){
            return new ResponseEntity<>("The source account does not belong to the authenticated client", HttpStatus.FORBIDDEN);
        }

        Transaction transactionCredit = new Transaction(TransactionType.CREDIT, amount, description, LocalDateTime.now());
        Transaction transactionDebit = new Transaction(TransactionType.DEBIT, amount, description, LocalDateTime.now());
        accountOrigin.addTransaction(transactionCredit);
        accountDestiny.addTransaction(transactionDebit);

        transactionRepository.save(transactionCredit);
        transactionRepository.save(transactionDebit);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
