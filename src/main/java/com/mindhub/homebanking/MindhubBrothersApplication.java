package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MindhubBrothersApplication {

	private final ClientRepository clientRepository;
	public MindhubBrothersApplication(ClientRepository clientRepository){
		this.clientRepository = clientRepository;

	}

	public static void main(String[] args) {
		SpringApplication.run(MindhubBrothersApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(){
		return args -> {
			Client client1 = new Client("melba","morel", "melba@mindhub.com");
			clientRepository.save(client1);
		};
	}
}
