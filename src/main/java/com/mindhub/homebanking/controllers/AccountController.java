package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private ClientService clientService;

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    @GetMapping("/accounts")
    public List<AccountDTO> getAccounts() {
        return accountService.getAccounts();
    }

    @GetMapping("/accounts/{id}")
    public ResponseEntity<AccountDTO> getAccount(@PathVariable Long id, Authentication authentication) {
        Client client = clientService.findByEmail(authentication.getName());
        Account account = accountService.findById(id);
        if (account == null || !account.getClient().equals(client)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(new AccountDTO(account));
    }

    @PostMapping("/clients/current/accounts")
    public ResponseEntity<Object> createAccount(@RequestParam AccountType type,
                                                Authentication authentication) {

        Client client = clientService.findByEmail(authentication.getName());

        if (client.getAccounts().size() <= 2) {
            String accountNumber;
            do {
                accountNumber = "VIN-" + (long) ((Math.random() * (99999999 - 10000000)) + 10000000);
            } while (accountService.findByNumber(accountNumber) != null);

            Account account = new Account(accountNumber, LocalDate.now(), 0.0, type);
            client.addAccount(account);
            accountService.saveAccount(account);

        }else {
            return new ResponseEntity<>("Client already has 3 accounts registered", HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Transactional
    @DeleteMapping("/clients/current/accounts")
    public ResponseEntity<Object> deleteAccount(@RequestParam Long id, Authentication authentication){
        Client client = clientService.findByEmail(authentication.getName());
        Account account = accountService.findById(id);

        if(!account.getClient().equals(client)){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        transactionRepository.deleteByAccount(account);

        accountRepository.delete(account);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}