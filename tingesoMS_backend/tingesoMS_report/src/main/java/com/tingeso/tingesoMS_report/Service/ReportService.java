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
    
    // RF6.2 List Clients with Delays
    // We can extract unique clients from Delayed Loans
    public List<String> getClientsWithDelays() {
         List<LoanDto> delayed = getDelayedLoans();
         return delayed.stream()
                 .map(LoanDto::getClientRut)
                 .distinct()
                 .collect(Collectors.toList());
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
