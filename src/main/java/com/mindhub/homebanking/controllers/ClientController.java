package com.mindhub.homebanking.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    private ClientService clientService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping("/clients")
    public List<ClientDTO> getClients(){
        return clientService.getClients();
    }

    @GetMapping("/clients/{id}")
    public ClientDTO getClient(@PathVariable Long id){
        return clientService.findByIdDTO(id);
    }
    @PostMapping("/clients")
    public ResponseEntity<Object> register(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String email,
            @RequestParam String password) throws JsonProcessingException {

        if (firstName.isBlank() || lastName.isBlank() || email.isBlank() || password.isBlank()) {
            return new ResponseEntity<>("fields are missing", HttpStatus.FORBIDDEN);
        }

        if (clientService.findByEmail(email) != null) {
            return new ResponseEntity<>("Email already in use", HttpStatus.FORBIDDEN);
        }

        // Verificar la existencia del correo electrónico utilizando la API de hunter.io
        RestTemplate restTemplate = new RestTemplate();
        String apiKey = "bdae3f20223e73cd0dde752e61fb68c12d8873c1";
        String url = "https://api.hunter.io/v2/email-verifier?email=" + email + "&api_key=" + apiKey;
        String response = restTemplate.getForObject(url, String.class);

        // Analizar la respuesta JSON para obtener el resultado de la verificación
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(response);
        boolean emailExists = rootNode.path("data").path("result").asText().equals("deliverable");

        if (!emailExists) {
            return new ResponseEntity<>("Email does not exist", HttpStatus.FORBIDDEN);
        }

        Client client = new Client(firstName, lastName, email, passwordEncoder.encode(password));

        clientService.saveClient(client);

        String accountNumber;
        do {
            accountNumber = "VIN-" + (long) ((Math.random() * (99999999 - 10000000)) + 10000000);
        } while (accountService.findByNumber(accountNumber) != null);

        Account account = new Account(accountNumber, LocalDate.now(), 0.0, AccountType.ORDINARY);
        client.addAccount(account);
        accountService.saveAccount(account);

        return new ResponseEntity<>( "the account was created successfully",HttpStatus.CREATED);
    }



    @GetMapping("/clients/current")
    public ClientDTO getAll(Authentication authentication) {
      return new ClientDTO(clientService.findByEmail(authentication.getName()));
    }
}
