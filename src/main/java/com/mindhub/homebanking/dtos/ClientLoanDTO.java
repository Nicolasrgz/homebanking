package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.models.Loan;

public class ClientLoanDTO {
    private long id;
    private double amount;
    private int payments;
    private long loanId;
    private String loanName;
    private Double loanInstallment;
    public ClientLoanDTO(){}
    public ClientLoanDTO(ClientLoan clientLoan) {
        this.id = clientLoan.getId();
        this.amount = clientLoan.getAmount();
        this.payments = clientLoan.getPayments();
        this.loanId = clientLoan.getLoan().getId();
        this.loanName = clientLoan.getLoan().getName();
        this.loanInstallment = clientLoan.getLoanInstallment();
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

    public long getLoanId() {
        return loanId;
    }

    public Double getLoanInstallment() {
        return loanInstallment;
    }

    public String getLoanName() {
        return loanName;
    }
}
