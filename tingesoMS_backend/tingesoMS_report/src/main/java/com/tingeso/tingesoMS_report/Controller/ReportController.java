package com.tingeso.tingesoMS_report.Controller;

import com.tingeso.tingesoMS_report.Dtos.LoanDto;
import com.tingeso.tingesoMS_report.Dtos.ToolRankingDto;
import com.tingeso.tingesoMS_report.Service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    // RF6.1
    @GetMapping("/active")
    public ResponseEntity<List<LoanDto>> getActiveLoans() {
        return ResponseEntity.ok(reportService.getActiveLoans());
    }

    // RF6.2
    @GetMapping("/delayed-clients")
    public ResponseEntity<List<String>> getClientsWithDelays() {
        return ResponseEntity.ok(reportService.getClientsWithDelays());
    }
    
    // Extra: Delayed Loans full details
    @GetMapping("/delayed")
    public ResponseEntity<List<LoanDto>> getDelayedLoans() {
        return ResponseEntity.ok(reportService.getDelayedLoans());
    }

    // RF6.3
    @GetMapping("/ranking")
    public ResponseEntity<List<ToolRankingDto>> getToolRanking() {
        return ResponseEntity.ok(reportService.getToolRanking());
    }
}
