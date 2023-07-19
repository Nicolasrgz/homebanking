package com.mindhub.homebanking.services.service;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;

import java.util.List;

public interface ClientService {

    Client findByEmail(String email);
    List<ClientDTO> getClients();
    ClientDTO findByIdDTO(Long id);
    void saveClient(Client client);

}
