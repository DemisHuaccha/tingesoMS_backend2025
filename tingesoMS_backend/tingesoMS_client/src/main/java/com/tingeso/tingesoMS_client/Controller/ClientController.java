package com.tingeso.tingesoMS_client.Controller;

import com.tingeso.tingesoMS_client.Dtos.CreateClientDto;
import com.tingeso.tingesoMS_client.Entities.Client;
import com.tingeso.tingesoMS_client.Service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping("/register")
    public ResponseEntity<Client> register(@RequestBody CreateClientDto client) {
        return ResponseEntity.ok(clientService.register(client));
    }
    
    @GetMapping("/byRut")
    public ResponseEntity<Client> getByRut(@RequestParam String rut) {
        Client c = clientService.findByRut(rut);
        if (c == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(c);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Client> getById(@PathVariable Long id) {
        Client c = clientService.findById(id);
        if (c == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(c);
    }

    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<Void> updateStatus(@PathVariable Long id) {
        clientService.updateStatus(id);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<Client>> getAll() {
        return ResponseEntity.ok(clientService.findAll());
    }
    
    @GetMapping("/byName/{name}")
    public ResponseEntity<List<Client>> getByName(@PathVariable String name) {
        return ResponseEntity.ok(clientService.findByName(name));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable Long id, @RequestBody Client client) {
        Client updated = clientService.updateCustomer(id, client);
        return ResponseEntity.ok(updated);
    }


    @GetMapping("/search")
    public ResponseEntity<List<String>> searchRuts(@RequestParam String rut) {
        return ResponseEntity.ok(clientService.searchRuts(rut));
    }

    @PostMapping("/getByIds")
    public ResponseEntity<List<Client>> getClientsByIds(@RequestBody List<Long> ids) {
        List<Client> clients = clientService.findAllById(ids);
        return ResponseEntity.ok(clients);
    }
}
