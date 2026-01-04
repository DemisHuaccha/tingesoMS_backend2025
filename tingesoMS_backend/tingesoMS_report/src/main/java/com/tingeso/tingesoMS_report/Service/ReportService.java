package com.tingeso.tingesoMS_report.Service;

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
import java.util.stream.Collectors;

@Service
public class ReportService {

    @Autowired
    private RestTemplate restTemplate;

    private static final String LOAN_SERVICE_URL = "http://tingesoMS_loan/api/Loan/report";

    public List<LoanDto> getActiveLoans() {
        try {
            ResponseEntity<List<LoanDto>> response = restTemplate.exchange(
                    LOAN_SERVICE_URL + "/active",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<LoanDto>>() {}
            );
            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
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
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
    
    private static final String CLIENT_SERVICE_URL = "http://tingesoMS_client/api/client";

    // RF6.2 List Clients with Delays
    public List<com.tingeso.tingesoMS_report.Dtos.ClientDto> getClientsWithDelays() {
         List<LoanDto> delayed = getDelayedLoans();
         List<String> ruts = delayed.stream()
                 .map(LoanDto::getClientRut)
                 .distinct()
                 .collect(Collectors.toList());
         
         List<com.tingeso.tingesoMS_report.Dtos.ClientDto> clients = new java.util.ArrayList<>();
         for(String rut : ruts) {
             try {
                // Assuming client service has endpoint getByRut
                ResponseEntity<com.tingeso.tingesoMS_report.Dtos.ClientDto> response = restTemplate.getForEntity(
                        CLIENT_SERVICE_URL + "/byRut?rut=" + rut,
                        com.tingeso.tingesoMS_report.Dtos.ClientDto.class
                );
                if(response.getBody() != null) {
                    clients.add(response.getBody());
                }
             } catch (Exception e) {
                 e.printStackTrace();
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
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
