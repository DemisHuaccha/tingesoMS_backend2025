package com.tingeso.tingesoMS_client.Controller;

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
    public ResponseEntity<Client> register(@RequestBody Client client) {
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
    public ResponseEntity<Void> updateStatus(@PathVariable Long id, @RequestParam(required = false) String status) {
        // Default to toggling or setting to Restricted/Active. 
        // For simplicity, M2 might send strict status.
        // Or if null, maybe logic.
        // But RF3.2 says "Change status".
        if(status == null) status = "RESTRICTED"; 
        clientService.updateStatus(id, status);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<Client>> getAll() {
        return ResponseEntity.ok(clientService.findAll());
    }
    
    @GetMapping("/byName")
    public ResponseEntity<List<Client>> getByName(@RequestParam String name) {
        return ResponseEntity.ok(clientService.findByName(name));
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<Client> updateCustomer(@PathVariable Long id, @RequestBody Client client) {
        Client updated = clientService.updateCustomer(id, client);
        if(updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updated);
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        clientService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<String>> searchRuts(@RequestParam String rut) {
        return ResponseEntity.ok(clientService.searchRuts(rut));
    }
}
