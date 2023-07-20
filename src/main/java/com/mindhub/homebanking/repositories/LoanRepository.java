package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.models.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface LoanRepository extends JpaRepository<Loan, Long> {

    Loan findByName(String name);
    List<Loan> findByPayments(Integer payments);

    Loan existById (ClientLoan clientLoan);

}
