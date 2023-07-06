package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Loan;

import java.util.List;

public class LoanApplicationDTO {
    private long id;
    private Double amount;
    private Integer payments;
    private String numberAccountDestiny;
    private String name;

    public LoanApplicationDTO(){}

    public LoanApplicationDTO(long id, Double amount, Integer payments, String numberAccountDestiny, String name) {
        this.id = id;
        this.amount = amount;
        this.payments = payments;
        this.numberAccountDestiny = numberAccountDestiny;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public Double getAmount() {
        return amount;
    }

    public Integer getPayments() {
        return payments;
    }

    public String getNumberAccountDestiny() {
        return numberAccountDestiny;
    }

    public String getName() {
        return name;
    }
}
