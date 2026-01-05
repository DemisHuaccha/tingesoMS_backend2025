package com.tingeso.tingesoMS_loan.Controller;

import com.tingeso.tingesoMS_loan.Dtos.ClientDto;
import com.tingeso.tingesoMS_loan.Dtos.CreateLoanRequest;
import com.tingeso.tingesoMS_loan.Dtos.LoanResponseDto;
import com.tingeso.tingesoMS_loan.Dtos.ReturnLoanDto;
import com.tingeso.tingesoMS_loan.Entities.Loan;
import com.tingeso.tingesoMS_loan.Service.LoanServiceImpl;
import com.tingeso.tingesoMS_loan.Services.Providers.ExternalServiceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/loan")
public class LoanController {

    @Autowired
    private LoanServiceImpl loanService;
    @Autowired
    private ExternalServiceProvider externalService;

    @GetMapping("/{id}")
    public ResponseEntity<Loan> getLoanById(@PathVariable Long id) {
        return loanService.getLoanById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/getDelayedClients")
    public ResponseEntity<List<ClientDto>> getDelayedClients(){
        return ResponseEntity.ok(externalService.getDelayedClientsDetails());
    }

    @PostMapping("/createLoan")
    public ResponseEntity<Loan> createLoan(@RequestBody CreateLoanRequest loan){
        String clientRut = loan.getClientRut();
        Long toolId= loan.getToolId();
        LocalDate deliveryDate= loan.getDeliveryDate();
        LocalDate expectedReturnDate= loan.getReturnDate();

        Loan loanSave= externalService.createLoan(clientRut,toolId,deliveryDate,expectedReturnDate);

        return ResponseEntity.ok(loanSave);
    }

    @PutMapping("/return")
    public ResponseEntity<Loan> returnLoan(@RequestBody ReturnLoanDto loanDto) {
        LocalDate date = LocalDate.now();
        Loan loan = loanService.returnLoan(loanDto.getLoanId(), date, loanDto);
        return ResponseEntity.ok(loan);
    }

    @PutMapping("/returnDamegeTool")
    public ResponseEntity<Loan> returnLoanDamage(@RequestBody ReturnLoanDto loanDto) {
        LocalDate date = LocalDate.now();
        Loan loan = loanService.returnLoanDamageTool(loanDto.getLoanId(), date, loanDto);
        return ResponseEntity.ok(loan);
    }

    @PutMapping("/returnDeleteTool")
    public ResponseEntity<Loan> returnLoanDelete(@RequestBody ReturnLoanDto loanDto) {
        LocalDate date = LocalDate.now();
        Loan loan = loanService.returnLoanDeleteTool(loanDto.getLoanId(), date, loanDto);
        return ResponseEntity.ok(loan);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<LoanResponseDto>> getAllLoans() {
        List<LoanResponseDto> loans = loanService.findAll();
        return ResponseEntity.ok(loans);
    }

    @GetMapping("/report/active")
    public ResponseEntity<List<Loan>> getActiveLoans() {
        return ResponseEntity.ok(loanService.findActiveAndOnTimeLoans());
    }

    @GetMapping("/report/delayed")
    public ResponseEntity<List<Loan>> getDelayedLoans() {
        return ResponseEntity.ok(loanService.findActiveAndDelayedLoans());
    }

    @GetMapping("/report/ranking")
    public ResponseEntity<List<com.tingeso.tingesoMS_loan.Dtos.ToolRankingDto>> getToolRanking() {
        return ResponseEntity.ok(loanService.findMostLoanedToolsWithDetails());
    }
}
