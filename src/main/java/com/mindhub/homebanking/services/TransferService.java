package com.mindhub.homebanking.services;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Transaction;

import java.util.List;

public interface TransferService {

    void saveTransfer(Transaction transaction);
    void deleteAccount(Account account);
    List<Transaction> findByAccount (Account account);
}
