package com.mindhub.homebanking.repository;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class RepositoriesTest {

    @Autowired
    LoanRepository loanRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    CardRepository cardRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    TransactionRepository transactionRepository;

    @Test
    public void existLoans(){
        List<Loan> loans = loanRepository.findAll();
        assertThat(loans,is(not(empty())));
    }

    @Test
    public void existMortgageLoan(){
        List<Loan> loans = loanRepository.findAll();
        assertThat(loans, hasItem(hasProperty("name", is("MORTGAGE"))));
    }

    @Test
    public void existEmail(){
        List<Client> email = clientRepository.findAll();
        assertThat(email, hasItem(hasProperty("email", is("melba@mindhub.com"))));
    }

    @Test
    public void existClient(){
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList,is(not(empty())));
    }

    @Test
    public void existNumberCard(){
        List<Card> number= cardRepository.findAll();
        assertThat(number, hasItem(hasProperty("number", is("0857-6181-1934-5445"))));
    }

    @Test
    public void existColorCard(){
        List<Card> color = cardRepository.findAll();
        assertThat(color, hasItem(hasProperty("color", Matchers.is(CardColor.GOLD))));
    }

    @Test
    public void existNumberAccount(){
        List<Account> accounts = accountRepository.findAll();
        assertThat(accounts, hasItem((hasProperty("number",is ("VIN001")))));
    }

    @Test
    public void existAccount(){
        List<Account> accounts = accountRepository.findAll();
        assertThat(accounts,is(not(empty())));
    }

    @Test
    public void existTransactions(){
        List<Transaction> transaction = transactionRepository.findAll();
        assertThat(transaction,is(not(empty())));
    }

    @Test
    public void existTransaction(){
        List<Transaction> transaction = transactionRepository.findAll();
        assertThat(transaction, notNullValue());
    }
}

