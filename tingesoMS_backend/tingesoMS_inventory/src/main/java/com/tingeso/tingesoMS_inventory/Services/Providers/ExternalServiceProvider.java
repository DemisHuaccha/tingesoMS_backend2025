package com.tingeso.tingesoMS_inventory.Services.Providers;

import com.tingeso.tingesoMS_inventory.Dtos.*;
import com.tingeso.tingesoMS_inventory.Entities.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.*;

@Service
public class ExternalServiceProvider {

    @Autowired
    private RestTemplate restTemplate;

    // --- CONSTANTES DE URL BASE ---
    private final String AUTH_URL = "http://tingeso-auth/api/auth";
    private final String KARDEX_URL = "http://tingeso-kardex/api/kardex/";

    //private final String AUTH_URL = "http://localhost:6001/api/auth/";
    //private final String KARDEX_URL = "http://localhost:6005/api/kardex/";

    // --- AUTH SERVICE ---

    public Optional<DtoClient> getUserByEmail(String email) {
        // Construimos la URL usando la constante AUTH_URL
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(AUTH_URL + "getByEmail")
                .queryParam("email", email)
                .encode()
                .toUriString();

        try {
            DtoClient user = restTemplate.getForObject(urlTemplate, DtoClient.class);
            return Optional.ofNullable(user);
        } catch (Exception e) {
            System.err.println("Error buscando usuario por email: " + e.getMessage());
            return Optional.empty();
        }
    }

    // --- KARDEX SERVICE ---

    /* 1. Notificar Creación de Préstamo */
    public void notifyKardexLoan(DtoLoan loan) {
        restTemplate.postForObject(KARDEX_URL + "saveLoan", loan, Void.class);
    }

    /* 2. Notificar Creación de Herramienta */
    public void notifyKardexTool(CreateToolDto request) {
        restTemplate.postForObject(KARDEX_URL + "saveTool", request, Void.class);
    }

    /* 3. Notificar Creación de Cliente */
    public void notifyKardexClient(DtoClient request) {
        restTemplate.postForObject(KARDEX_URL + "saveClient", request, Void.class);
    }

    /* 4. Notificar Actualización de Herramienta */
    public void notifyKardexUpdateTool(CreateToolDto request) {
        restTemplate.postForObject(KARDEX_URL + "saveUpdateTool", request, Void.class);
    }

    /* 5. Notificar Cambio de Estado de Herramienta */
    public void notifyKardexUpdateStatusTool(ToolStatusDto request) {
        restTemplate.postForObject(KARDEX_URL + "saveUpdateStatusTool", request, Void.class);
    }

    /* 6. Notificar Reparación de Herramienta */
    public void notifyKardexRepairTool(CreateToolDto request) {
        restTemplate.postForObject(KARDEX_URL + "saveRepairTool", request, Void.class);
    }

    /* 7. Notificar Eliminación de Herramienta */
    public void notifyKardexDeleteTool(CreateToolDto request) {
        restTemplate.postForObject(KARDEX_URL + "saveDeleteTool", request, Void.class);
    }
}
