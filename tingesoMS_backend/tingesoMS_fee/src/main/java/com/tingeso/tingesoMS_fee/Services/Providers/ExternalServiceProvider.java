package com.tingeso.tingesoMS_fee.Services.Providers;

import com.tingeso.tingesoMS_fee.Entities.DtoTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

@Service
public class ExternalServiceProvider {

    @Autowired
    private RestTemplate restTemplate;

    public void updateToolInInventory(DtoTool dtoTool) {
        try {
             restTemplate.put("http://tingeso-inventory/api/inventory/update", dtoTool);
        } catch (Exception e) {
            System.err.println("Error forwarding to Inventory: " + e.getMessage());
            throw new RuntimeException("Failed to update Inventory from Fee Service");
        }
    }
}
