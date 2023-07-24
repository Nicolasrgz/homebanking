package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.enums.AccountType;
import com.mindhub.homebanking.models.enums.CardColor;
import com.mindhub.homebanking.models.enums.CardType;
import com.mindhub.homebanking.models.enums.TransactionType;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

@SpringBootApplication
public class MindhubBrothersApplication {

@Autowired
private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(MindhubBrothersApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, LoanRepository loanRepository, ClientLoanRepository clientLoanRepository
	, CardRepository cardRepository) {
		return args -> {
			Client melba = new Client("melba", "morel", "melba@mindhub.com",passwordEncoder.encode("superman"));
			clientRepository.save(melba);
			Account account1 = new Account("VIN001", LocalDate.now(), 5000.00, AccountType.ORDINARY);
			melba.addAccount(account1);
			accountRepository.save(account1);
			Account account2 = new Account("VIN002", LocalDate.now().plusDays(1), 7500.00, AccountType.SAVING);
			melba.addAccount(account2);
			accountRepository.save(account2);


			Transaction one = new Transaction(TransactionType.CREDIT, 3000.00, "venta de un perro", LocalDateTime.now(), account1.getBalance());
			Transaction two = new Transaction(TransactionType.DEBIT, 3000.00, "pago de alquiler", LocalDateTime.now(), account1.getBalance());
			Transaction three = new Transaction(TransactionType.CREDIT, 600.00, "venta de una remera", LocalDateTime.now(), account2.getBalance());
			Transaction four = new Transaction(TransactionType.DEBIT, 1000.00, "pago de nafta", LocalDateTime.now(), account2.getBalance());
			account1.addTransaction(one);
			account1.addTransaction(two);
			account2.addTransaction(three);
			account2.addTransaction(four);
			transactionRepository.save(one);
			transactionRepository.save(two);
			transactionRepository.save(three);
			transactionRepository.save(four);


			Loan mortgage = new Loan("MORTGAGE", 500000, Arrays.asList(12, 24, 36, 48, 60), 0.20);
			Loan personnel = new Loan("PERSONNEL", 100000, Arrays.asList(6, 12, 24), 0.20);
			Loan automotive = new Loan("AUTOMOTIVE", 300000, Arrays.asList(6, 12, 24, 36), 0.20);
			loanRepository.save(mortgage);
			loanRepository.save(personnel);
			loanRepository.save(automotive);

			Card card = new Card(CardType.CREDIT, CardColor.GOLD, "melba morel",	"0000-0000-0000-0000", 100, LocalDateTime.now().minusDays(1), LocalDateTime.now());
			melba.addCards(card);
			cardRepository.save(card);


			Client admin = new Client("admin", "admin", "admin@gmail.com",passwordEncoder.encode("123"));
			clientRepository.save(admin);
		};
	}
}
