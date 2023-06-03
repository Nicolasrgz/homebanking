package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class MindhubBrothersApplication {

	private final ClientRepository clientRepository;
	private final AccountRepository accountRepository;
	public MindhubBrothersApplication(ClientRepository clientRepository, AccountRepository accountRepository){
		this.clientRepository = clientRepository;

		this.accountRepository = accountRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(MindhubBrothersApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(){
		return args -> {
			Client client1 = new Client("melba","morel", "melba@mindhub.com");
			clientRepository.save(client1);

			Account account1 = new Account("VIN001", LocalDate.now(), 5000, client1 );
			accountRepository.save(account1);

			Account account2 = new Account("VIN002", LocalDate.now().plusDays(1), 7500, client1 );
			accountRepository.save(account2);

			client1.addAccount(account1);
			client1.addAccount(account2);
		};
	}
}
