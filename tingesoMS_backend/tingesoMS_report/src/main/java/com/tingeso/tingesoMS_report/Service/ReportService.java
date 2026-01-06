package com.tingeso.tingesoMS_report.Service;

import com.tingeso.tingesoMS_report.Dtos.ClientDto;
import com.tingeso.tingesoMS_report.Dtos.LoanDto;
import com.tingeso.tingesoMS_report.Dtos.ToolRankingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ReportService {

        @Autowired
        private RestTemplate restTemplate;

        // Ajustado a nombres estándar de microservicios
        private static final String LOAN_SERVICE_URL = "http://tingesoMS-loan/api/loans/report";
        private static final String CLIENT_SERVICE_URL = "http://tingesoMS-client/api/clients";
        //private static final String LOAN_SERVICE_URL = "http://localhost:6006/api/loans/report";
        //private static final String CLIENT_SERVICE_URL = "http://localhost:6002/api/client";



        public List<LoanDto> getActiveLoans() {
            try {
                // Se quitó el slash extra
                ResponseEntity<List<LoanDto>> response = restTemplate.exchange(
                        LOAN_SERVICE_URL + "/active",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<LoanDto>>() {}
                );
                return response.getBody();
            } catch (Exception e) {
                return Collections.emptyList();
            }
        }

        public List<LoanDto> getDelayedLoans() {
            try {
                ResponseEntity<List<LoanDto>> response = restTemplate.exchange(
                        LOAN_SERVICE_URL + "/delayed",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<LoanDto>>() {}
                );
                return response.getBody();
            } catch (Exception e) {
                return Collections.emptyList();
            }
        }

        // RF6.2 List Clients with Delays
        public List<ClientDto> getClientsWithDelays() {
            List<LoanDto> delayed = getDelayedLoans();

            // Obtenemos los RUTs únicos
            List<String> ruts = delayed.stream()
                    .map(LoanDto::getClientRut)
                    .filter(Objects::nonNull)
                    .distinct()
                    .collect(Collectors.toList());

            if (ruts.isEmpty()) return Collections.emptyList();

            // RECOMENDACIÓN: En lugar de un for, usa un endpoint masivo si existe
            // Si no existe el endpoint masivo, el for funciona pero es lento:
            List<ClientDto> clients = new java.util.ArrayList<>();
            for(String rut : ruts) {
                try {
                    ResponseEntity<ClientDto> response = restTemplate.getForEntity(
                            CLIENT_SERVICE_URL + "/byRut/" + rut, // Estructura REST más común
                            ClientDto.class
                    );
                    if(response.getBody() != null) {
                        clients.add(response.getBody());
                    }
                } catch (Exception e) {
                    System.err.println("Error buscando cliente RUT: " + rut);
                }
            }
            return clients;
        }

        public List<ToolRankingDto> getToolRanking() {
            try {
                ResponseEntity<List<ToolRankingDto>> response = restTemplate.exchange(
                        LOAN_SERVICE_URL + "/ranking",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<ToolRankingDto>>() {}
                );
                return response.getBody();
            } catch (Exception e) {
                return Collections.emptyList();
            }
        }
}
