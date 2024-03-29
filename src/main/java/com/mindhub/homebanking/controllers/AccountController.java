package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.enums.AccountType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.services.service.AccountService;
import com.mindhub.homebanking.services.service.ClientService;
import com.mindhub.homebanking.services.service.TransferService;
import com.mindhub.homebanking.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private TransferService transferService;

    @GetMapping("/accounts")
    public List<AccountDTO> getAccounts() {
        return accountService.getAccounts();
    }

    @PatchMapping("/accounts/{Id}/deactivate")
    public ResponseEntity<Object> deactivateAccount(@PathVariable Long Id) {
        Optional<Account> accountOpt = Optional.ofNullable(accountService.findById(Id));

        List<AccountDTO> accountDTOS = accountService.getAccounts().stream().filter(accountDTO -> accountDTO.isActive() == null).collect(Collectors.toList());

        if (accountOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Account account = accountOpt.get();
        if (account.getBalance() > 0){
            return new ResponseEntity<>("your account has funds, please move those funds before being deactivated", HttpStatus.FORBIDDEN);
        }
        account.setActive(true);
        accountService.saveAccount(account);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/accounts/{id}")
    public ResponseEntity<Object> getAccount(@PathVariable Long id, Authentication authentication, Account accounts) {

        Client client = clientService.findByEmail(authentication.getName());
        Account account = accountService.findById(id);

        if(account.isActive() != accounts.isActive()){
            return new ResponseEntity<>("the page you are trying to access does not exist", HttpStatus.FORBIDDEN);
        }

        if (!account.getClient().equals(client)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(new AccountDTO(account));
    }

    @PostMapping("/clients/current/accounts")
    public ResponseEntity<Object> createAccount(@RequestParam AccountType type,
                                                Authentication authentication) {

        Client client = clientService.findByEmail(authentication.getName());

            String accountNumber;
            do {
                accountNumber = AccountUtils.getAccountNumber();
            } while (accountService.findByNumber(accountNumber) != null);

            Account account = new Account(accountNumber, LocalDate.now(), 0.0, type);
            client.addAccount(account);
            accountService.saveAccount(account);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}