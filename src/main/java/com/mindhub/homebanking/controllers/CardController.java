package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class CardController {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private CardRepository cardRepository;
    @RequestMapping(path = "/clients/current/cards", method = RequestMethod.POST)
    public ResponseEntity<Object> createCards(
            @RequestParam CardColor color,
            @RequestParam CardType type,
            Authentication authentication) {
        Client client = clientRepository.findByEmail(authentication.getName());
        if (client.getCards().size() <= 2) {
            String cardNumber;
            long cvvNumber;
            do {
                cardNumber = String.format("%04d-%04d-%04d-%04d", (int)(Math.random()*10000), (int)(Math.random()*10000), (int)(Math.random()*10000), (int)(Math.random()*10000));
            } while (cardRepository.findByNumber(cardNumber) != null);

            do {
                cvvNumber =  (long) ((Math.random() * (999 - 100)) + 100);
            } while (cardRepository.findByCvv(cvvNumber) != null);

            Card card = new Card(client.getFirstName() + client.getLastName(), type, color, cardNumber, cvvNumber, LocalDateTime.now().plusYears(5), LocalDateTime.now());
            client.addCards(card);
            cardRepository.save(card);
        }else {
            return new ResponseEntity<>("Client already has 3 cards registered", HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
