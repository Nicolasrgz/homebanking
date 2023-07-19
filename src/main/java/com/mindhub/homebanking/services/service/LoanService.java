package com.mindhub.homebanking.services.service;

import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.Loan;

import java.util.List;

public interface LoanService {
    Loan findByName(String name);
    List<Loan> findByPayments(Integer payments);
    List<LoanDTO>getLoans();
    void loanSave(Loan loan);
}
