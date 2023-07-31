package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.enums.CardColor;
import com.mindhub.homebanking.models.enums.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.services.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardServiceImplement implements CardService {

    @Autowired
    private CardRepository cardRepository;
    @Override
    public Card findByNumber(String number) {
        return cardRepository.findByNumber(number);
    }

    @Override
    public long countByTypeAndClient(CardType type, Client client) {
        return cardRepository.countByTypeAndClient(type, client);
    }

    @Override
    public long countByColorAndTypeAndClient(CardColor color, CardType type, Client client) {
        return cardRepository.countByColorAndTypeAndClient(color, type, client);
    }

    @Override
    public void cardSave(Card card) {
        cardRepository.save(card);
    }

    @Override
    public Card findById(long id) {
        return cardRepository.findById(id);
    }
}
