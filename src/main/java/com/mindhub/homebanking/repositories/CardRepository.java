package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CardRepository extends JpaRepository<Card, Long> {
    public Card findByNumber(String number);
    public long countByTypeAndClient(CardType type, Client client);
    public long countByColorAndTypeAndClient(CardColor color, CardType type, Client client);
}


