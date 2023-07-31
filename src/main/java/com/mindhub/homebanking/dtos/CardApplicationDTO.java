package com.mindhub.homebanking.dtos;

public class CardApplicationDTO {
    private long id;
    private String number;
    private Long cvv;
    private Double amount;
    private String description;

    public CardApplicationDTO(long id, String number, Long cvv, Double amount, String description) {
        this.id = id;
        this.number = number;
        this.cvv = cvv;
        this.amount = amount;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public Long getCvv() {
        return cvv;
    }

    public Double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }
}
