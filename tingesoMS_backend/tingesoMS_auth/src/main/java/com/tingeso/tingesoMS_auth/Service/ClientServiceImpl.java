package com.tingeso.tingesoMS_user.Service;

import com.tingeso.tingesoMS_user.Dtos.CreateClientDto;
import com.tingeso.tingesoMS_user.Entities.Client;
import com.tingeso.tingesoMS_user.Repository.ClientRepositorie;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    ClientRepositorie clientRepositorie;

    //Create
    @Override
    public Client save(CreateClientDto clientDto) {
        Optional<Client> clientP=clientRepositorie.findByRut(clientDto.getRut());
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
        clientRepositorie.save(client);
        return client;
    }

    //Read
    @Override
    public Client findByName(String name){
        return clientRepositorie.findCustomerByFirstName(name).orElse(null);
    }
    @Override
    public Client findById(Long id){
        return clientRepositorie.findById(id).orElse(null);
    }
    @Override
    public List<Client> findAll() {
        return clientRepositorie.findAll();
    }

    //Update
    @Override
    public void updateCustomer(Client client, Long id){
        Client oldcustomer = clientRepositorie.findById(id).orElse(null);
        if(oldcustomer!=null){
            oldcustomer.setFirstName(client.getFirstName());
            oldcustomer.setLastName(client.getLastName());
            oldcustomer.setRut(client.getRut());
            oldcustomer.setEmail(client.getEmail());
            oldcustomer.setPhone(client.getPhone());
            clientRepositorie.save(oldcustomer);
        }else {
            throw new EntityNotFoundException("Client with ID " + id + " not found");
        }
    }

    @Override
    public void updateStatusCustomer(Long idClient){
        Client client = clientRepositorie.findById(idClient).orElse(null);
        if(client !=null){
            if(client.getStatus().equals(Boolean.TRUE)){
                client.setStatus(Boolean.FALSE);
            }
            else {
                client.setStatus(Boolean.TRUE);
            }
        }
        clientRepositorie.save(client);
    }

    //Delete
    @Override
    public void deleteCustomer(Client client){
        client.setStatus(Boolean.FALSE);
        clientRepositorie.save(client); // Ensure save called
    }

    @Override
    public List<String> searchRuts(String partialRut) {
        return clientRepositorie.findByRutContainingIgnoreCase(partialRut)
                .stream()
                .map(Client::getRut)
                .toList();
    }

}
