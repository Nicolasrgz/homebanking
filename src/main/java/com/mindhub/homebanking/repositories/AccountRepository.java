package com.mindhub.homebanking.repositories;


import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.enums.AccountType;
import com.mindhub.homebanking.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByNumber (String number);
    Account findByAccountType (AccountType accountType);
    List<Account> findByClient(Client client);
}
