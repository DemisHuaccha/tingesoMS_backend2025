package com.tingeso.tingesoMS_client.Service;

import com.tingeso.tingesoMS_client.Dtos.CreateClientDto;
import com.tingeso.tingesoMS_client.Entities.Client;
import com.tingeso.tingesoMS_client.Repository.ClientRepositorie;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    private ClientRepositorie clientRepo;

    @InjectMocks
    private ClientService clientService;

    private Client createAndSaveClient(String rut, String name, String email) {
        Client client = new Client();
        client.setRut(rut);
        client.setFirstName(name);
        client.setLastName("TestLastname");
        client.setEmail(email);
        client.setPhone("+56911111111");
        client.setStatus(true);
        return clientRepo.save(client);
    }

    @Test
    void register() {
        CreateClientDto dto = new CreateClientDto();
        dto.setFirstName("Ana");
        dto.setLastName("Torres");
        dto.setRut("12.345.678-9");
        dto.setEmailC("ana@example.com"); // Según tu DTO usas emailC
        dto.setPhone("+56912345678");

        Client saved = clientService.register(dto);

        assertNotNull(saved.getIdCustomer());
        assertEquals("12.345.678-9", saved.getRut());
        assertTrue(saved.getStatus());
    }

    @Test
    void findByRut() {
        Client client = new Client();
        client.setRut("12345678-9");
        when(clientRepo.findByRut("12345678-9")).thenReturn(Optional.of(client));

        Client result = clientService.findByRut("12345678-9");
        assertNotNull(result);
        assertEquals("12345678-9", result.getRut());
    }
    
    @Test
    void updateStatus() {
        Client client = new Client();
        client.setIdCustomer(1L);
        client.setStatus(Boolean.TRUE);
        
        when(clientRepo.findById(1L)).thenReturn(Optional.of(client));
        
        clientService.updateStatus(1L);
        
        assertEquals(Boolean.FALSE, client.getStatus());
        verify(clientRepo).save(client);
    }
    
    @Test
    void findAll() {
        Client client = new Client();
        client.setRut("12345678-9");
        when(clientRepo.findAll()).thenReturn(java.util.List.of(client));

        java.util.List<Client> result = clientService.findAll();
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }
    
    @Test
    void findByName() {
        Client client = new Client();
        client.setName("John");
        when(clientRepo.findByName("John")).thenReturn(java.util.List.of(client));
        
        java.util.List<Client> result = clientService.findByName("John");
        assertFalse(result.isEmpty());
        assertEquals("John", result.get(0).getName());
    }
    
    @Test
    void updateCustomer() {
        Client client = new Client();
        client.setIdCustomer(1L);
        client.setName("Old Name");
        
        Client updates = new Client();
        updates.setName("New Name");
        
        when(clientRepo.findById(1L)).thenReturn(Optional.of(client));
        when(clientRepo.save(any(Client.class))).thenReturn(client);
        
        Client result = clientService.updateCustomer(1L, updates);
        
        assertNotNull(result);
        assertEquals("New Name", result.getName());
    }

    
    @Test
    void searchRuts() {
        Client c = new Client();
        c.setRut("11.111.111-1");
        when(clientRepo.findByRutContaining("11.111")).thenReturn(java.util.List.of(c));
        
        java.util.List<String> results = clientService.searchRuts("11.111");
        assertFalse(results.isEmpty());
        assertEquals("11.111.111-1", results.get(0));
    }
    
    @Test
    void findDelayedClient() {
        // Logic mocks external usage, so returning empty list logic is tested.
        assertNotNull(clientService.findDelayedClient());
    }
    
    @Test
    void restrictClientsWithDelayedLoans() {
        // As findDelayedClient returns empty, this loop does nothing, but ensures no exception.
        clientService.restrictClientsWithDelayedLoans();
    }
    
    @Test
    void findById() {
        Client client = new Client();
        client.setIdCustomer(1L);
        when(clientRepo.findById(1L)).thenReturn(Optional.of(client));
        
        Client result = clientService.findById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getIdCustomer());
    }
}
