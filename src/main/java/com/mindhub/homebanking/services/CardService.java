package com.mindhub.homebanking.services;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;

public interface CardService {

    Card findByNumber(String number);
    long countByTypeAndClient(CardType type, Client client);
    long countByColorAndTypeAndClient(CardColor color, CardType type, Client client);
    void cardSave(Card card);
}
