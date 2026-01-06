package com.tingeso.tingesoMS_client.Services.Providers;

import com.tingeso.tingesoMS_client.Dtos.CreateClientDto;
import com.tingeso.tingesoMS_client.Entities.DtoUser;
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

    //private final String KARDEX_URL = "http://tingeso-kardex/api/kardex/";
    //private final String KARDEX_URL = "http://localhost:6005/api/kardex/";

    private final String KARDEX_URL = "http://tingesoMS-kardex/api/kardex/";
    private final String USER_URL = "http://tingesoMS-auth/api/auth/";

    //private final String KARDEX_URL = "http://tingeso-kardex/api/kardex/";
    //private final String KARDEX_URL = "http://localhost:6005/api/kardex/";

    //private final String KARDEX_URL = "http://tingeso-kardex/api/kardex/";
    //private final String USER_URL = "http://localhost:6001/api/auth/";


    public void notifyKardexClient(CreateClientDto request) {
        restTemplate.postForObject(KARDEX_URL + "saveClient", request, Void.class);
    }

    /**
     * Obtiene un usuario por su email desde el microservicio de Auth.
     */
    public Optional<DtoUser> getUserByEmail(String email) {
        // Construimos la URL: http://localhost:6001/api/auth/getByEmail?email=...
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(USER_URL + "getByEmail")
                .queryParam("email", email)
                .encode()
                .toUriString();

        try {
            // Hacemos el GET al microservicio de Auth
            DtoUser user = restTemplate.getForObject(urlTemplate, DtoUser.class);
            return Optional.ofNullable(user);
        } catch (Exception e) {
            // Si el controlador devuelve 404 o hay error de conexión, capturamos la excepción
            System.err.println("Error buscando usuario por email en Auth Service: " + e.getMessage());
            return Optional.empty();
        }
    }
}
