package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Loan;

import java.util.List;

public class LoanDTO {
    private long id;
    private Double amount;
    private List<Integer> payments;
    private String name;
    private double percentage;
    public LoanDTO(){}

    public LoanDTO(Loan loan) {
        this.id = loan.getId();
        this.amount = loan.getMaxAmount();
        this.payments = loan.getPayments();
        this.name = loan.getName();
        this.percentage = loan.getPercentage();
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

    public String getName() {
        return name;
    }
    public double getPercentage() {
        return percentage;
    }
}
