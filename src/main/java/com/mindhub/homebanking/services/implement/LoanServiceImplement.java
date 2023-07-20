package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.repositories.LoanRepository;
import com.mindhub.homebanking.services.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class LoanServiceImplement implements LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Override
    public Loan findByName(String name) {
        return loanRepository.findByName(name);
    }

    @Override
    public List<Loan> findByPayments(Integer payments) {
        return loanRepository.findByPayments(payments);
    }

    @Override
    public List<LoanDTO> getLoans() {
        return loanRepository.findAll().stream().map(LoanDTO::new).collect(Collectors.toList());
    }

    @Override
    public void loanSave(Loan loan) {
        loanRepository.save(loan);
    }

    @Override
    public Loan findById(long id) {
        return loanRepository.findById(id).orElse(null);
    }

    @Override
    public Loan existById(ClientLoan clientLoan) {
        return loanRepository.existById(clientLoan);
    }
}
