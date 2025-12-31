package com.tingeso.tingesoMS_loan.Service;

import com.tingeso.tingesoMS_loan.Dtos.LoanResponseDto;
import com.tingeso.tingesoMS_loan.Dtos.ToolRankingDto;
import com.tingeso.tingesoMS_loan.Entities.Loan;

import java.time.LocalDate;
import java.util.List;

public interface LoanService {
    Boolean isToolAvailableForClient(Long clientId, String toolName, String toolCategory, Integer loanFee);
    Loan createLoan(String clientRut, Long toolId, LocalDate deliveryDate, LocalDate expectedReturnDate);
    Loan returnLoan(Long loanId, LocalDate actualReturnDate);
    Loan returnLoanDamageTool(Long loanId, LocalDate actualReturnDate);
    Loan returnLoanDeleteTool(Long loanId, LocalDate actualReturnDate);
    List<LoanResponseDto> findAll();
    
    // Instead of returning Client Entity, return IDs
    List<Long> findClientDelayed();
    
    List<Loan> findActiveAndOnTimeLoans();
    List<Loan> findActiveAndDelayedLoans();
    List<ToolRankingDto> findMostLoanedToolsWithDetails();
}
