package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.LoanRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.Arrays;

@RestController
@RequestMapping("/api")
public class LoanController {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    @Transactional
    @PostMapping("/loans")
    public ResponseEntity<Object> createLoans(@RequestBody LoanApplicationDTO loan,
                                              Loan loans,
                                              Authentication authentication){
        //cliente autenticado
        Client client = clientRepository.findByEmail(authentication.getName());

        //diferentes prestamos que puede acceder
        Loan MORTGAGE = new Loan("MORTGAGE", 500000, Arrays.asList(12, 24, 36, 48, 60));
        Loan PERSONNEL = new Loan("PERSONNEL", 500000, Arrays.asList(6, 12, 24));
        Loan AUTOMOTIVE = new Loan("AUTOMOTIVE", 500000, Arrays.asList(6, 12, 24, 36));
        loanRepository.save(MORTGAGE);
        loanRepository.save(PERSONNEL);
        loanRepository.save(AUTOMOTIVE);

        //verifica que los campos esten completos
        if (loan.getAmount().isNaN() || loan.getNumberAccountDestiny().isBlank()|| loan.getPayments().isEmpty()){
            return new ResponseEntity<>("has unfilled fields", HttpStatus.FORBIDDEN);
        }

        Account accountDestiny = accountRepository.findByNumber(loan.getNumberAccountDestiny());//cuenta destino

        //verifico que el nombre del prestamo exista
        if (loanRepository.findByName(loan.getName()) != null) {
            return new ResponseEntity<>("the loan you are requesting does not exist", HttpStatus.FORBIDDEN);
        }
        //el monto debe ser mayor a 0
        if (loan.getAmount() <= 0 ){
            return new ResponseEntity<>("the amount has to be greater than 0", HttpStatus.FORBIDDEN);
        }
        //verifico que las cuotas sean del prestamo
        if (loanRepository.findByPayments(loan.getPayments()) != null){
            return new ResponseEntity<>("the installments you are trying to select do not belong to the loan", HttpStatus.FORBIDDEN);
        }
        //verifico que el monto no exceda las 500lucas
        if (loan.getAmount() > loans.getMaxAmount() ){
            return new ResponseEntity<>("the requested amount exceeds the loan limit", HttpStatus.FORBIDDEN);
        }
        //verifico que la cuenta de destino exista
        if (accountRepository.findByNumber(loan.getNumberAccountDestiny()) == null){
            return new ResponseEntity<>("destination account does not exist", HttpStatus.FORBIDDEN);
        }
        //Verify that the source account belongs to the authenticated client
        if (!accountDestiny.getClient().equals(client)){
            return new ResponseEntity<>("The destination account does not belong to the authenticated client", HttpStatus.FORBIDDEN);
        }




        return new ResponseEntity<>(HttpStatus.CREATED);
    }



}
