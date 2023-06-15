package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.models.Loan;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class ClientLoanDTO {
    private long id;
    private double amount;
    private int payments;
    private Client debtor;
    private Loan loan;

    public ClientLoanDTO(ClientLoan clientLoan) {
        this.id = clientLoan.getId();
        this.amount = clientLoan.getAmount();
        this.payments = clientLoan.getPayments();
        this.debtor = clientLoan.getDebtor();
        this.loan = clientLoan.getLoan();
    }

    public long getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public int getPayments() {
        return payments;
    }

    public Client getDebtor() {
        return debtor;
    }

    public Loan getLoan() {
        return loan;
    }
}
