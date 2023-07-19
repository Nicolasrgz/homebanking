package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;

import java.util.List;

public interface AccountService {

    List<AccountDTO>getAccounts();

    Account findById (long id);
    Account findByNumber (String number);
    List<Account> findByClient(Client client);
    void saveAccount(Account account);
    void deleteAccount (Account account);
}
