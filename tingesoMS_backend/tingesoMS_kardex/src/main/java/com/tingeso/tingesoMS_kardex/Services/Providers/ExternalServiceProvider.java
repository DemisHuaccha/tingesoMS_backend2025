package com.tingeso.tingesoMS_kardex.Services.Providers;


import com.tingeso.tingesoMS_kardex.Dtos.DtoClient;
import com.tingeso.tingesoMS_kardex.Dtos.DtoLoan;
import com.tingeso.tingesoMS_kardex.Dtos.DtoTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@Service
public class ExternalServiceProvider {

    @Autowired
    private RestTemplate restTemplate;

    // --- CONSTANTES DE URL BASE ---
    private final String AUTH_URL = "http://tingesoMS-auth/api/auth/";
    private final String INVENTORY_TOOL_URL = "http://tingesoMS_inventory/api/inventory/";
    private final String LOAN_URL = "http://tingesoMS-loan/api/loan/";

    //private final String AUTH_URL = "http://localhost:6001/api/auth/";
    //private final String INVENTORY_TOOL_URL = "http://localhost:6004/api/inventory/";
    //private final String LOAN_URL = "http://localhost:6006/api/loan/";

    /**
     * Obtiene un usuario por su email desde el servicio de Auth.
     */
    public Optional<DtoClient> getUserByEmail(String email) {
        // Construimos la URL usando la constante y el endpoint
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

    /**
     * Obtiene una herramienta por su ID desde el servicio de Inventario.
     */
    public DtoTool getToolById(Long id) {
        try {
            return restTemplate.getForObject(INVENTORY_TOOL_URL + id, DtoTool.class);
        } catch (Exception e) {
            System.err.println("Error obteniendo herramienta ID " + id + ": " + e.getMessage());
            return null;
        }
    }

    /**
     * Obtiene un préstamo por su ID desde el servicio de Loans.
     */
    public Optional<DtoLoan> getLoanById(Long id) {
        try {
            DtoLoan loan = restTemplate.getForObject(LOAN_URL + id, DtoLoan.class);
            return Optional.ofNullable(loan);
        } catch (Exception e) {
            System.err.println("Error obteniendo préstamo ID " + id + ": " + e.getMessage());
            return Optional.empty();
        }
    }
}
