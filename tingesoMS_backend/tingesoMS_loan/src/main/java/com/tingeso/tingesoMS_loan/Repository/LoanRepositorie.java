package com.tingeso.tingesoMS_loan.Repository;

import com.tingeso.tingesoMS_loan.Dtos.LoanResponseDto;
import com.tingeso.tingesoMS_loan.Dtos.ToolRankingDto;
import com.tingeso.tingesoMS_loan.Entities.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepositorie extends JpaRepository<Loan,Long> {

    List<Loan> findByClientIdAndLoanStatusTrue(Long clientId);

    List<Loan> findByClientIdAndLoanStatusTrueAndPenaltyTrue(Long clientId);


    @Query("SELECT new com.tingeso.tingesoMS_loan.Dtos.LoanResponseDto(l.idLoan, l.deliveryDate, l.returnDate, l.loanStatus, l.penalty, l.penaltyTotal, l.clientRut, l.toolId, l.priceToPay) FROM Loan l")
    List<LoanResponseDto> findAllWithClientAndToolIds();

    @Query("SELECT l FROM Loan l WHERE l.loanStatus = true AND l.penalty = false")
    List<Loan> findActiveAndOnTimeLoans();

    @Query("SELECT l FROM Loan l WHERE l.loanStatus = true AND l.returnDate < CURRENT_DATE")
    List<Loan> findActiveAndDelayedLoans();

    @Query("SELECT DISTINCT l.clientId FROM Loan l WHERE l.penalty = true")
    List<Long> findClientIdsWithDelayedLoans();

    @Query("SELECT new com.tingeso.tingesoMS_loan.Dtos.ToolRankingDto(l.toolName, l.toolCategory, l.toolLoanFee, COUNT(l)) FROM Loan l GROUP BY l.toolName, l.toolCategory, l.toolLoanFee ORDER BY COUNT(l) DESC")
    List<ToolRankingDto> findMostLoanedToolsWithDetails();


    @Query("SELECT COUNT(l) >= 1 FROM Loan l WHERE l.clientId = :clientId AND l.loanStatus = true AND l.toolName = :toolName AND l.toolCategory = :toolCategory AND l.toolLoanFee = :toolLoanFee")
    boolean clientHasNoMatchingLoan(@Param("clientId") Long clientId, @Param("toolName") String toolName, @Param("toolCategory") String toolCategory, @Param("toolLoanFee") Integer toolLoanFee);

}
