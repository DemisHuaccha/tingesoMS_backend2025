package com.tingeso.tingesoMS_report.Service;

import com.tingeso.tingesoMS_report.Dtos.LoanDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReportServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ReportService reportService;

    @Test
    void getActiveLoans() {
        LoanDto loan = new LoanDto();
        loan.setIdLoan(1L);
        List<LoanDto> list = Collections.singletonList(loan);
        
        when(restTemplate.exchange(
                eq("http://tingesoMS_loan/api/Loan/report/active"),
                eq(HttpMethod.GET),
                isNull(),
                any(ParameterizedTypeReference.class)
        )).thenReturn(ResponseEntity.ok(list));
        
        List<LoanDto> result = reportService.getActiveLoans();
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void getDelayedLoans() {
        LoanDto loan = new LoanDto();
        loan.setIdLoan(2L);
        List<LoanDto> list = Collections.singletonList(loan);

        when(restTemplate.exchange(
                eq("http://tingesoMS_loan/api/Loan/report/delayed"),
                eq(HttpMethod.GET),
                isNull(),
                any(ParameterizedTypeReference.class)
        )).thenReturn(ResponseEntity.ok(list));

        List<LoanDto> result = reportService.getDelayedLoans();
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }
}
