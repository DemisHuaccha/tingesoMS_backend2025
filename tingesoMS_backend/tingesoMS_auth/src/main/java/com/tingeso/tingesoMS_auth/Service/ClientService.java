package com.tingeso.tingesoMS_user.Service;

import com.tingeso.tingesoMS_user.Dtos.CreateClientDto;
import com.tingeso.tingesoMS_user.Entities.Client;

import java.util.List;

public interface ClientService {
    Client save(CreateClientDto clientDto);
    Client findByName(String name);
    Client findById(Long id);
    List<Client> findAll();
    // findDelayedClient removed as it depends on Loan
    void updateCustomer(Client client, Long id);
    void updateStatusCustomer(Long idClient);
    void deleteCustomer(Client client);
    // restrictClientsWithDelayedLoans removed as it depends on Loan
    List<String> searchRuts(String partialRut);
}
