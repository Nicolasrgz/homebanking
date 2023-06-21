package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class MindhubBrothersApplication {

	public static void main(String[] args) {
		SpringApplication.run(MindhubBrothersApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, LoanRepository loanRepository, ClientLoanRepository clientLoanRepository
	, CardRepository cardRepository) {
		return args -> {
			Client melba = new Client("melba", "morel", "melba@mindhub.com");
			clientRepository.save(melba);
			Account account1 = new Account("VIN001", LocalDate.now(), 5000);
			Account account2 = new Account("VIN002", LocalDate.now().plusDays(1), 7500);
			melba.addAccount(account1);
			melba.addAccount(account2);
			accountRepository.save(account1);
			accountRepository.save(account2);
			Transaction one = new Transaction(TransactionType.CREDIT, 3000, "venta de un perro", LocalDateTime.now());
			Transaction two = new Transaction(TransactionType.DEBIT, -3000, "pago de alquiler", LocalDateTime.now());
			Transaction three = new Transaction(TransactionType.CREDIT, 600, "venta de una remera", LocalDateTime.now());
			Transaction four = new Transaction(TransactionType.DEBIT, -1000, "pago de nafta", LocalDateTime.now());
			account1.addTransaction(one);
			account1.addTransaction(two);
			account2.addTransaction(three);
			account2.addTransaction(four);
			transactionRepository.save(one);
			transactionRepository.save(two);
			transactionRepository.save(three);
			transactionRepository.save(four);
			Loan Hipotecario = new Loan("Hipotecario", 500000, Arrays.asList(12, 24, 36, 48, 60));
			Loan Personal = new Loan("Personal", 500000, Arrays.asList(6, 12, 24));
			Loan Automotriz = new Loan("Automotriz", 500000, Arrays.asList(6, 12, 24, 36));
			loanRepository.save(Hipotecario);
			loanRepository.save(Personal);
			loanRepository.save(Automotriz);
			ClientLoan prestamo1 = new ClientLoan(400000, 60);
			ClientLoan prestamo2 = new ClientLoan(50000, 12);
			melba.addClientLoans(prestamo1);
			melba.addClientLoans(prestamo2);
			Hipotecario.addClientLoans(prestamo1);
			Personal.addClientLoans(prestamo2);
			clientLoanRepository.save(prestamo1);
			clientLoanRepository.save(prestamo2);

			Card card1 = new Card("melba morel", CardType.DEBIT, CardColor.GOLD, "0000 1111 2222 3333", 189,LocalDateTime.now().plusYears(5), LocalDateTime.now() );
			Card card2 = new Card("melba morel", CardType.CREDIT, CardColor.TITANIUM, "0000 1111 2222 3334", 777,LocalDateTime.now().plusYears(5), LocalDateTime.now() );
			melba.addCards(card1);
			melba.addCards(card2);
			cardRepository.save(card1);
			cardRepository.save(card2);


			Client juan = new Client("juan", "Rondo", "juna@gmail.com");
			clientRepository.save((juan));
			Account account3 = new Account("VIN003", LocalDate.now(), 5000);
			Account account4 = new Account("VIN004", LocalDate.now().plusDays(1), 7500);
			juan.addAccount(account3);
			juan.addAccount(account4);
			clientRepository.save((juan));
			accountRepository.save(account3);
			accountRepository.save(account4);
			Transaction five = new Transaction(TransactionType.DEBIT, -600, "compras del super", LocalDateTime.now());
			Transaction six = new Transaction(TransactionType.DEBIT, -3000, "pago de tarjeta", LocalDateTime.now());
			Transaction seven = new Transaction(TransactionType.DEBIT, -3700, "compras del bazar", LocalDateTime.now());
			Transaction eight = new Transaction(TransactionType.DEBIT, -3000, "pago de deudas", LocalDateTime.now());
			account3.addTransaction(five);
			account4.addTransaction(six);
			account3.addTransaction(seven);
			account4.addTransaction(eight);
			transactionRepository.save(five);
			transactionRepository.save(six);
			transactionRepository.save(seven);
			transactionRepository.save(eight);
			ClientLoan prestamo3 = new ClientLoan(100000, 24);
			ClientLoan prestamo4 = new ClientLoan(200000, 36);
			juan.addClientLoans(prestamo3);
			juan.addClientLoans(prestamo4);
			Personal.addClientLoans(prestamo3);
			Automotriz.addClientLoans(prestamo4);
			clientLoanRepository.save(prestamo3);
			clientLoanRepository.save(prestamo4);
			Card card3 = new Card("juan Rondo", CardType.DEBIT, CardColor.SILVER, "1234 1111 2222 3333", 100,LocalDateTime.now().plusYears(5), LocalDateTime.now() );
			juan.addCards(card3);
			cardRepository.save(card3);
		};
	}
}
