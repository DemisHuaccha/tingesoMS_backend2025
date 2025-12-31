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
}
