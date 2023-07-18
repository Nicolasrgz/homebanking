package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.services.CardService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.utils.CardUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CardController {

    @Autowired
    private ClientService clientService;
    @Autowired
    private CardService cardService;
    @Autowired
    private CardRepository cardRepository;
    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> createCards(
            @RequestParam CardColor color,
            @RequestParam CardType type,
            Authentication authentication) {

        long cvvNumber = CardUtils.getCvvNumber();
        String cardNumber;

        Client client = clientService.findByEmail(authentication.getName());

        if (client.getCards().size() >= 6) {
            return new ResponseEntity<>("Client already has 6 cards registered", HttpStatus.FORBIDDEN);
        }

        if (cardService.countByTypeAndClient(type,client) >= 3) {
            return new ResponseEntity<>("The client already has 3 registered " + type + " cards.", HttpStatus.FORBIDDEN);
        }

        if ( cardService.countByColorAndTypeAndClient(color, type, client) >= 1) {
            return new ResponseEntity<>("The client already has a " + color + " " + type + " card.", HttpStatus.FORBIDDEN);
        }

        do {
            cardNumber = CardUtils.getCardNumber();
        } while (cardService.findByNumber(cardNumber) != null);

        Card card = new Card(type, color, client.getFirstName()+ " " + client.getLastName(), cardNumber, cvvNumber, LocalDateTime.now().plusYears(5), LocalDateTime.now());
        client.addCards(card);
        cardService.cardSave(card);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

        @PatchMapping("/card/{Id}/deactivate")
        public ResponseEntity<Object> deactivateCard(@PathVariable Long Id) {
            Optional<Card> cardOpt = cardRepository.findById(Id);
            if (cardOpt.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            Card card = cardOpt.get();
            card.setIsActive(true);
            cardRepository.save(card);
            return ResponseEntity.ok().build();
        }

}

