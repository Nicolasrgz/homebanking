package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountDTO {
    private long Id;
    private String number;
    private LocalDate creationDate;
    private double balance;
    private Set<TransactionDTO> transactions;
    private AccountType accountType;
    private boolean isActive;
    public AccountDTO() {
    }

    public AccountDTO(Account account) {
        this.Id = account.getId();
        this.balance = account.getBalance();
        this.creationDate = account.getCreationDate();
        this.number = account.getNumber();
        this.transactions = account.getTransactions()
                .stream()
                .map(TransactionDTO::new)
                .collect(Collectors.toSet());
        this.accountType = account.getAccountType();
        this.isActive = account.isActive();
    }

    public Set<TransactionDTO> getAccounts() {
        return transactions;
    }

    public long getId() {
        return Id;
    }

    public String getNumber() {
        return number;
    }
    public LocalDate getCreationDate() {
        return creationDate;
    }
    public double getBalance() {
        return balance;
    }
    public boolean isActive() {
        return isActive;
    }
    public AccountType getAccountType() {
        return accountType;
    }
}
