package com.mindhub.homebanking.services.service;

import com.mindhub.homebanking.models.ClientLoan;

public interface ClientLoanService {
    void clientLoanSave(ClientLoan clientLoan);
    ClientLoan findById (long id);
}
