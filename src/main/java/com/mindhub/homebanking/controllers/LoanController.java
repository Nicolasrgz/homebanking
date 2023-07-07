package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import com.mindhub.homebanking.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class LoanController {

    @Autowired
    private ClientService clientService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private LoanService loanService;
    @Autowired
    private TransferService transferService;
    @Autowired
    private ClientLoanService clientLoanService;

    @GetMapping("/loans")
    public List<LoanDTO> getLoans() {
        return loanService.getLoans();
    }

    @Transactional
    @PostMapping("/loans")
    public ResponseEntity<Object> createLoans(@RequestBody
                                              LoanApplicationDTO loan, Authentication authentication){
        //cliente autenticado
        Client client = clientService.findByEmail(authentication.getName());

        //diferentes prestamos que puede acceder
        Loan mortgage = loanService.findByName("MORTGAGE");
        if (mortgage == null) {
            mortgage = new Loan("MORTGAGE", 500000, Arrays.asList(12, 24, 36, 48, 60));
            loanService.loanSave(mortgage);
        }
        Loan personnel = loanService.findByName("PERSONNEL");
        if (personnel == null) {
            personnel = new Loan("PERSONNEL", 100000, Arrays.asList(6, 12, 24));
            loanService.loanSave(personnel);
        }
        Loan automotive = loanService.findByName("AUTOMOTIVE");
        if (automotive == null) {
            automotive = new Loan("AUTOMOTIVE", 300000, Arrays.asList(6, 12, 24, 36));
            loanService.loanSave(automotive);
        }


        //verifica que los campos esten completos
        if (loan.getAmount().isNaN() || loan.getNumberAccountDestiny().isBlank()|| loan.getPayments() == null){
            return new ResponseEntity<>("has unfilled fields", HttpStatus.FORBIDDEN);
        }

        Account accountDestiny = accountService.findByNumber(loan.getNumberAccountDestiny());//cuenta destino

        //verifico que el nombre del prestamo exista
        if (loanService.findByName(loan.getName()) == null) {
            return new ResponseEntity<>("the loan you are requesting does not exist", HttpStatus.FORBIDDEN);
        }
        //el monto debe ser mayor a 0
        if (loan.getAmount() <= 0 ){
            return new ResponseEntity<>("the amount has to be greater than 0", HttpStatus.FORBIDDEN);
        }
        //verifico que las cuotas sean del prestamo
        if (loanService.findByPayments(loan.getPayments()) == null){
            return new ResponseEntity<>("the installment you are trying to select does not belong to the loan", HttpStatus.FORBIDDEN);
        }

        //verifico que el monto no sea mayor al maximo del prestamo
        Loan loanType = loanService.findByName(loan.getName());
        if (loan.getAmount() > loanType.getMaxAmount()) {
            return new ResponseEntity<>("the requested amount exceeds the loan limit", HttpStatus.FORBIDDEN);
        }
        //verifico que la cuenta de destino exista
        if (accountService.findByNumber(loan.getNumberAccountDestiny()) == null){
            return new ResponseEntity<>("destination account does not exist", HttpStatus.FORBIDDEN);
        }
        //Verify that the source account belongs to the authenticated client
        if (!accountDestiny.getClient().equals(client)){
            return new ResponseEntity<>("The destination account does not belong to the authenticated client", HttpStatus.FORBIDDEN);
        }

        List<Loan> loans = loanService.findByPayments(loan.getPayments());
        if (loans.isEmpty()) {
            return new ResponseEntity<>("the installment you are trying to select does not belong to the loan", HttpStatus.FORBIDDEN);
        }

        //le sumo el 20% en intereses
        double totalAmount = loan.getAmount() + (loan.getAmount() * 0.20);
        ClientLoan loan1 = new ClientLoan( totalAmount, loan.getPayments());
        loan1.setDebtor(client);

        if (loan.getName().equals("AUTOMOTIVE")){
            automotive.addClientLoans(loan1);
            clientLoanService.clientLoanSave(loan1);
        }
        if (loan.getName().equals("PERSONNEL")){
            personnel.addClientLoans(loan1);
            clientLoanService.clientLoanSave(loan1);
        }
        if (loan.getName().equals("MORTGAGE")){
            mortgage.addClientLoans(loan1);
            clientLoanService.clientLoanSave(loan1);
        }

        Transaction transactionCredit = new Transaction(TransactionType.CREDIT, loan.getAmount(), loan.getName() + " " + "loan approved", LocalDateTime.now());
        accountDestiny.addTransaction(transactionCredit);
        transferService.saveTransfer(transactionCredit);

        Double credit = accountDestiny.getBalance() + loan.getAmount();

        accountDestiny.setBalance(credit);
        accountService.saveAccount(accountDestiny);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
