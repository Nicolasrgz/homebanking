package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.enums.CardColor;
import com.mindhub.homebanking.models.enums.CardType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CardRepository extends JpaRepository<Card, Long> {
     Card findByNumber(String number);
     Card findById(long id);
     long countByTypeAndClient(CardType type, Client client);
     long countByColorAndTypeAndClient(CardColor color, CardType type, Client client);
}


