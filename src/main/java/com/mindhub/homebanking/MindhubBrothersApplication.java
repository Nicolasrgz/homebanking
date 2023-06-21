package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootApplication
public class MindhubBrothersApplication {

	public static void main(String[] args) {
		SpringApplication.run(MindhubBrothersApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository){
		return args -> {
			Client melba = new Client("melba","morel", "melba@mindhub.com");
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


			Client juan = new Client("juan", "rondon", "juna@gmail.com");
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



		};
	}
}
