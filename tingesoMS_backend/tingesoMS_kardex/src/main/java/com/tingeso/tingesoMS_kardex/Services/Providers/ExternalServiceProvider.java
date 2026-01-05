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

    public Optional<DtoClient> getUserByEmail(String email) {
        String url = "http://tingeso-auth/getByEmail";

        String urlTemplate = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("email", email)
                .encode()
                .toUriString();

        try {
            DtoClient user = restTemplate.getForObject(urlTemplate, DtoClient.class);
            return Optional.ofNullable(user); // Devuelve Optional con el usuario o vacío si es null
        } catch (Exception e) {
            System.err.println("Error buscando usuario por email: " + e.getMessage());
            return Optional.empty(); // En caso de error (como un 404), devuelve un Optional vacío
        }
    }

    public DtoTool getToolById(Long id) {
        try {
            return restTemplate.getForObject("http://tingesoMS_inventory/api/tool/" + id, DtoTool.class);
        } catch (Exception e) {
            return null;
        }
    }

    public Optional<DtoLoan> getLoanById(Long id) {
        // Usamos el nombre con el que el microservicio se registra en Eureka
        String url = "http://tingeso-loan/api/loans/" + id;

        try {
            DtoLoan loan = restTemplate.getForObject(url, DtoLoan.class);
            return Optional.ofNullable(loan);
        } catch (Exception e) {
            System.err.println("Error obteniendo préstamo ID " + id + ": " + e.getMessage());
            return Optional.empty();
        }
    }
}
