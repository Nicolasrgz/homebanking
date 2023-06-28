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
        String cardNumber;
        long cvvNumber;

        CardType cardTypeD = CardType.DEBIT;
        CardType cardTypeC = CardType.CREDIT;

        CardColor gold = CardColor.GOLD;
        CardColor silver = CardColor.SILVER;
        CardColor titanium = CardColor.TITANIUM;

        Client client = clientRepository.findByEmail(authentication.getName());

        if (client.getCards().size() <= 6 ) {

            if ( cardRepository.findByType(cardTypeD) <= 3 ){

                if (cardRepository.findByColor(gold) == 1 && cardRepository.findByColor(silver) == 1 && cardRepository.findByColor(titanium) == 1){
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
                    return new ResponseEntity<>("already owns a card of that color", HttpStatus.FORBIDDEN);
                }
            }else {
                return new ResponseEntity<>("The client already has 3 registered debit cards.", HttpStatus.FORBIDDEN);
            }
            if (cardRepository.findByType(cardTypeC) <= 3){

                if (cardRepository.findByColor(gold) == 1 && cardRepository.findByColor(silver) == 1 && cardRepository.findByColor(titanium) == 1){
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
                    return new ResponseEntity<>("already owns a card of that color", HttpStatus.FORBIDDEN);
                }
            }else {
                return new ResponseEntity<>("The client already has 3 registered credit cards.", HttpStatus.FORBIDDEN);
            }
        }else {
            return new ResponseEntity<>("Client already has 6 cards registered", HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
