package com.tingeso.tingesoMS_client.Service;

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

    @Test
    void register() {
        Client client = new Client();
        client.setRut("12345678-9");
        when(clientRepo.save(any(Client.class))).thenReturn(client);
        
        Client result = clientService.register(client);
        
        assertNotNull(result);
        assertEquals("12345678-9", result.getRut());
        assertEquals("ACTIVE", result.getStatus());
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
        client.setStatus("ACTIVE");
        
        when(clientRepo.findById(1L)).thenReturn(Optional.of(client));
        
        clientService.updateStatus(1L, "RESTRICTED");
        
        assertEquals("RESTRICTED", client.getStatus());
        verify(clientRepo).save(client);
    }
}
