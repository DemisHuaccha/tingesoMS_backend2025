package com.tingeso.tingesoMS_loan.Controller;

import com.tingeso.tingesoMS_loan.Dtos.CreateLoanRequest;
import com.tingeso.tingesoMS_loan.Dtos.LoanResponseDto;
import com.tingeso.tingesoMS_loan.Dtos.ReturnLoanDto;
import com.tingeso.tingesoMS_loan.Entities.Loan;
import com.tingeso.tingesoMS_loan.Service.LoanServiceImpl;
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

    // TODO: Cardex Feign
    // @Autowired
    // private CardexServiceImpl cardexServiceImpl;


    @PostMapping("/createLoan")
    public ResponseEntity<Loan> createLoan(@RequestBody CreateLoanRequest loan){
        String clientRut = loan.getClientRut();
        Long toolId= loan.getToolId();
        LocalDate deliveryDate= loan.getDeliveryDate();
        LocalDate expectedReturnDate= loan.getReturnDate();

        Loan loanSave= loanService.createLoan(clientRut,toolId,deliveryDate,expectedReturnDate);
        // TODO: Cardex
        // cardexServiceImpl.saveCardexLoan(toolId, loan.getEmail(), loanSave);
        return ResponseEntity.ok(loanSave);
    }

    @PutMapping("/return")
    public ResponseEntity<Loan> returnLoan(@RequestBody ReturnLoanDto loanDto) {
        LocalDate date = LocalDate.now();
        Loan loan = loanService.returnLoan(loanDto.getLoanId(), date);
        // cardexServiceImpl.saveCardexReturnLoan(loanDto);
        return ResponseEntity.ok(loan);
    }

    @PutMapping("/returnDamegeTool")
    public ResponseEntity<Loan> returnLoanDamage(@RequestBody ReturnLoanDto loanDto) {
        LocalDate date = LocalDate.now();
        Loan loan = loanService.returnLoanDamageTool(loanDto.getLoanId(), date);
        // cardexServiceImpl.saveCardexReturnLoanDamage(loanDto);
        return ResponseEntity.ok(loan);
    }

    @PutMapping("/returnDeleteTool")
    public ResponseEntity<Loan> returnLoanDelete(@RequestBody ReturnLoanDto loanDto) {
        LocalDate date = LocalDate.now();
        Loan loan = loanService.returnLoanDeleteTool(loanDto.getLoanId(), date);
        // cardexServiceImpl.saveCardexReturnLoanDelete(loanDto);
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
