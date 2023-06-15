package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
public class ClientLoan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private double amount;
    private int payments;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="debtor")
    private Client debtor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "loan")
    private Loan loan;

    public ClientLoan(){}

    public ClientLoan(double amount, int payments) {
        this.amount = amount;
        this.payments = payments;
    }

    public long getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getPayments() {
        return payments;
    }

    public void setPayments(int payments) {
        this.payments = payments;
    }

    public Client getDebtor() {
        return debtor;
    }

    public void setDebtor(Client debtor) {
        this.debtor = debtor;
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }
}
