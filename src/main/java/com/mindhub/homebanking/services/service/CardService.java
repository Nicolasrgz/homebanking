package com.mindhub.homebanking.services.service;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.enums.CardColor;
import com.mindhub.homebanking.models.enums.CardType;
import com.mindhub.homebanking.models.Client;

public interface CardService {
    Card findById(long id);
    Card findByNumber(String number);
    long countByTypeAndClient(CardType type, Client client);
    long countByColorAndTypeAndClient(CardColor color, CardType type, Client client);
    void cardSave(Card card);
}
