package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Loan;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ClientDTO {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private Set<AccountDTO> accounts;
    private List<Loan> loans;
    public ClientDTO() {}

    public ClientDTO(Client client) {
        this.id = client.getId();
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.email = client.getEmail();
        this.accounts = client.getAccounts()
                .stream()
                .map(AccountDTO::new)
                .collect(Collectors.toSet());
        this.loans = client.getLoans();
    }

    public List<Loan> getLoans() {
        return loans;
    }

    public Set<AccountDTO> getAccounts() {
        return accounts;
    }

    public long getId() {
        return id;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getEmail() {
        return email;
    }

}
