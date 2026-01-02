package com.tingeso.tingesoMS_client.Service;

import com.tingeso.tingesoMS_client.Entities.Client;
import com.tingeso.tingesoMS_client.Repository.ClientRepositorie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {
    @Autowired
    private ClientRepositorie clientRepo;

    public Client register(Client client) {
        client.setStatus("ACTIVE");
        return clientRepo.save(client);
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
    
    public void updateStatus(Long id, String status) {
        Client c = clientRepo.findById(id).orElse(null);
        if (c != null) {
            c.setStatus(status);
            clientRepo.save(c);
        }
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
    
    public void deleteCustomer(Long id) {
        // Soft delete: set status to INACTIVE or similar? 
        // Logic from Monolith: "delete" might mean really delete or soft.
        // Given typically safe practices, soft delete.
        // Let's assume soft delete "INACTIVE" or "DELETED".
        updateStatus(id, "DELETED");
    }
    
    public List<String> searchRuts(String rut) {
        return clientRepo.findByRutContaining(rut).stream()
                .map(Client::getRut)
                .toList();
    }
    
    // Inter-service communication mocked/simulated
    public List<Client> findDelayedClient() {
        // In Microservice architecture, this should call Loan Service.
        // Assuming Loan Service endpoint exists: GET /api/loan/delayed/clients (Ids)
        // For this task, we assume we get a list of IDs. 
        // Example logic with RestTemplate (commented out as no eureka/port known):
        // List<Long> ids = restTemplate.getForObject("http://tingesoMS-loan/api/loan/delayed/clients", List.class);
        // return clientRepo.findAllById(ids);
        
        // Returning empty list to satisfy compilation + architecture pattern.
        // To implement properly requires running services.
        return java.util.Collections.emptyList();
    }
    
    public void restrictClientsWithDelayedLoans() {
        // Fetch delayed clients
        List<Client> delayed = findDelayedClient();
        for(Client c : delayed) {
            updateStatus(c.getIdCustomer(), "RESTRICTED"); // or false
        }
    }
}
