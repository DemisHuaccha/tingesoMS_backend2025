package com.tingeso.tingesoMS_client.Service;

import com.tingeso.tingesoMS_client.Dtos.CreateClientDto;
import com.tingeso.tingesoMS_client.Entities.Client;
import com.tingeso.tingesoMS_client.Repository.ClientRepositorie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    @Autowired
    private ClientRepositorie clientRepo;


    public Client register(CreateClientDto clientDto) {
        Optional<Client> clientP=clientRepo.findByRut(clientDto.getRut());
        if(clientP.isPresent()){
            throw new IllegalArgumentException("Client with rut " + clientDto.getRut() + " already exists");
        }
        Client client = new Client();
        client.setRut(clientDto.getRut());
        client.setFirstName(clientDto.getFirstName());
        client.setLastName(clientDto.getLastName());
        client.setEmail(clientDto.getEmailC());
        client.setPhone(clientDto.getPhone());
        client.setStatus(Boolean.TRUE);
        clientRepo.save(client);
        return client;
    }
    
    public Client findByRut(String rut) {
        return clientRepo.findByRut(rut).orElse(null);
    }
    
    public Client findById(Long id) {
        return clientRepo.findById(id).orElse(null);
    }
    
    public List<Client> findAll() {
        return clientRepo.findAll();
    }

    public Client updateStatus(Long id) {
        Client client = clientRepo.findById(id).orElse(null);
        if (client != null) {
            if (client.getStatus().equals(Boolean.TRUE)) {
                client.setStatus(Boolean.FALSE);
            } else {
                client.setStatus(Boolean.TRUE);
            }
        }
        return clientRepo.save(client);
    }
    
    public List<Client> findByName(String name) {
        return clientRepo.findByName(name);
    }
    
    public Client updateCustomer(Long id, Client clientDetails) {
        Client client = findById(id);
        if(client != null) {
            client.setName(clientDetails.getName());
            client.setEmail(clientDetails.getEmail());
            client.setPhone(clientDetails.getPhone());
            // Add other fields as needed
            return clientRepo.save(client);
        }
        return null;
    }

    public List<String> searchRuts(String partialRut) {
        return clientRepo.findByRutContainingIgnoreCase(partialRut)
                .stream()
                .map(Client::getRut)
                .toList();
    }
    
    // Inter-service communication mocked/simulated
    public List<Client> findDelayedClient() {

        return java.util.Collections.emptyList();
    }
    
    public void restrictClientsWithDelayedLoans() {
        // Fetch delayed clients
        List<Client> delayed = findDelayedClient();

    }

    public List<Client> findAllById(List<Long> ids) {
        return clientRepo.findAllById(ids);
    }

}
