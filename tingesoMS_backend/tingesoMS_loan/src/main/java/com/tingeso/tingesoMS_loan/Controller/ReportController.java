package com.tingeso.tingesoMS_loan.Controller;

import com.tingeso.tingesoMS_loan.Dtos.LoanResponseDto;
import com.tingeso.tingesoMS_loan.Service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = "*")
public class ReportController {

    @Autowired
    private LoanService loanService;

    @GetMapping("/active-loans")
    public ResponseEntity<List<LoanResponseDto>> getActiveLoans() {
        List<LoanResponseDto> allLoans = loanService.findAll();
        List<LoanResponseDto> activeLoans = allLoans.stream()
                .filter(LoanResponseDto::getLoanStatus)
                .collect(Collectors.toList());
        return ResponseEntity.ok(activeLoans);
    }

    @GetMapping("/delinquent-clients")
    public ResponseEntity<List<Long>> getDelinquentClients() {
        List<Long> delinquentClients = loanService.findClientDelayed();
        return ResponseEntity.ok(delinquentClients);
    }
}
