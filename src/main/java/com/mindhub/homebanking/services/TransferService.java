package com.mindhub.homebanking.services;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Transaction;

public interface TransferService {

    void saveTransfer(Transaction transaction);
    void deleteAccount(Account account);
}
