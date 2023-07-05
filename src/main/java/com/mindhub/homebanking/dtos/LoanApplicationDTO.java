package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.models.LoanName;

import java.util.List;

public class LoanApplicationDTO {
    private long id;
    private Double amount;
    private List<Integer> payments;
    private String numberAccountDestiny;
    private LoanName name;

    LoanApplicationDTO(){}

    LoanApplicationDTO(Loan loan,String numberAccountDestiny, LoanName name){
        this.id = loan.getId();
        this.amount = loan.getMaxAmount();
        this.payments = loan.getPayments();
        this.numberAccountDestiny = numberAccountDestiny;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public Double getAmount() {
        return amount;
    }

    public List<Integer> getPayments() {
        return payments;
    }

    public String getNumberAccountDestiny() {
        return numberAccountDestiny;
    }

    public LoanName getName() {
        return name;
    }
}
