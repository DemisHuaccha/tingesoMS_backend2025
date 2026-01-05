package com.tingeso.tingesoMS_loan.Service;

import com.tingeso.tingesoMS_loan.Dtos.*;
import com.tingeso.tingesoMS_loan.Entities.Loan;
import com.tingeso.tingesoMS_loan.Repository.LoanRepositorie;
import com.tingeso.tingesoMS_loan.Services.Providers.ExternalServiceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LoanServiceImpl {

    @Autowired
    private LoanRepositorie loanRepo;
    
    @Autowired
    private ExternalServiceProvider externalService;

    public Boolean isToolAvailableForClient(Long clientId, String toolName, String toolCategory, Integer loanFee) {
        return loanRepo.clientHasNoMatchingLoan(clientId, toolName, toolCategory, loanFee);
    }



    // ... (rest of methods)

    public List<LoanResponseDto> findAll() {
        LocalDate today = LocalDate.now();
        List<Loan> allLoans = loanRepo.findAll();
        List<Loan> toSave = new ArrayList<>();

        for (Loan loan : allLoans) {
            if (Boolean.TRUE.equals(loan.getLoanStatus())) {
                long daysLate = ChronoUnit.DAYS.between(loan.getReturnDate(), today);
                long daysloanfee = ChronoUnit.DAYS.between(loan.getDeliveryDate(), loan.getReturnDate());
                int daysloanfeeInt = (int) daysloanfee;
                boolean hasPenalty = daysLate > 0;

                int penaltyVal = loan.getPenaltyForDelay() != null ? loan.getPenaltyForDelay() : 0;
                int feeVal = loan.getToolLoanFee() != null ? loan.getToolLoanFee() : 0;
                
                int totalPenalty = hasPenalty ? (int) (daysLate * penaltyVal) : 0;

                int newPriceToPay = (feeVal * daysloanfeeInt) + totalPenalty;
                
                loan.setPriceToPay(newPriceToPay);
                if (!java.util.Objects.equals(loan.getPenalty(), hasPenalty) || !java.util.Objects.equals(loan.getPenaltyTotal(), totalPenalty)) {
                    loan.setPenalty(hasPenalty);
                    loan.setPenaltyTotal(totalPenalty);
                    externalService.updateClientStatus(loan.getClientId());
                    toSave.add(loan);
                }
            }
        }

        if (!toSave.isEmpty()) {
            loanRepo.saveAll(toSave);
        }
        
        return loanRepo.findAllWithClientAndToolIds();
    }


    @Transactional
    public Loan returnLoan(Long loanId, LocalDate actualReturnDate, ReturnLoanDto dto) {
        Loan loan = loanRepo.findById(loanId)
                .orElseThrow(() -> new IllegalArgumentException("Loan not found"));

        if (!loan.getLoanStatus()) {
            throw new IllegalStateException("Loan already returned");
        }

        // Obtener datos de la herramienta desde el microservicio de Inventario
        ToolDto tool = Optional.ofNullable(externalService.getToolById(loan.getToolId()))
                .orElseThrow(() -> new IllegalStateException("Tool info not found"));

        loan.setLoanStatus(false);

        long daysLate = ChronoUnit.DAYS.between(loan.getReturnDate(), actualReturnDate);
        if (daysLate > 0) {
            int multa = (int) (daysLate * tool.getPenaltyForDelay());
            loan.setPenalty(true);
            loan.setPenaltyTotal(multa);
        } else {
            loan.setPenalty(false);
            loan.setPenaltyTotal(0);
        }

        // Liberar herramienta en Inventory MS
        ToolDto status= new ToolDto();
        status.setIdTool(loan.getToolId());
        externalService.updateToolStatus(status);

        loan.setPriceToPay(loan.getPriceToPay());

        // Habilitar cliente en Client MS
        externalService.updateClientStatus(loan.getClientId());
        externalService.notifyKardexReturnLoan(dto);

        return loanRepo.save(loan);
    }

    @Transactional
    public Loan returnLoanDamageTool(Long loanId, LocalDate actualReturnDate, ReturnLoanDto dto) {
        Loan loan = loanRepo.findById(loanId)
                .orElseThrow(() -> new IllegalArgumentException("Loan not found"));

        if (!loan.getLoanStatus()) {
            throw new IllegalStateException("Loan already returned");
        }

        ToolDto tool = Optional.ofNullable(externalService.getToolById(loan.getToolId()))
                .orElseThrow(() -> new IllegalStateException("Tool info not found"));

        loan.setLoanStatus(false);

        long daysLate = ChronoUnit.DAYS.between(loan.getReturnDate(), actualReturnDate);

        int delayPenalty = (daysLate > 0) ? (int) (daysLate * tool.getPenaltyForDelay()) : 0;
        int totalPenalty = delayPenalty + tool.getDamageValue();

        loan.setPenalty(true);
        loan.setPenaltyTotal(totalPenalty);

        ToolDto status= new ToolDto();
        status.setIdTool(loan.getToolId());
        // Actualizar herramienta: status=true, underRepair=true en Inventory MS
        externalService.updateToolDamageStatus(status);

        int currentPrice = loan.getPriceToPay() != null ? loan.getPriceToPay() : 0;
        loan.setPriceToPay(currentPrice + tool.getDamageValue());

        externalService.updateClientStatus(loan.getClientId());
        externalService.notifyKardexReturnLoanDamage(dto);

        return loanRepo.save(loan);
    }

    @Transactional
    public Loan returnLoanDeleteTool(Long loanId, LocalDate actualReturnDate, ReturnLoanDto dto) {
        Loan loan = loanRepo.findById(loanId)
                .orElseThrow(() -> new IllegalArgumentException("Loan not found"));

        if (!loan.getLoanStatus()) {
            throw new IllegalStateException("Loan already returned");
        }

        ToolDto tool = Optional.ofNullable(externalService.getToolById(loan.getToolId()))
                .orElseThrow(() -> new IllegalStateException("Tool info not found"));

        loan.setLoanStatus(false);

        long daysLate = ChronoUnit.DAYS.between(loan.getReturnDate(), actualReturnDate);

        int delayPenalty = (daysLate > 0) ? (int) (daysLate * tool.getPenaltyForDelay()) : 0;
        int totalPenalty = delayPenalty + tool.getReplacementValue();

        loan.setPenalty(true);
        loan.setPenaltyTotal(totalPenalty);

        ToolDto status= new ToolDto();
        status.setIdTool(loan.getToolId());

        // Actualizar herramienta: status=false, underRepair=false, deleteStatus=false
        externalService.returnLoanDeleteTool(status);

        int currentPrice = loan.getPriceToPay() != null ? loan.getPriceToPay() : 0;
        loan.setPriceToPay(currentPrice + tool.getReplacementValue());

        externalService.updateClientStatus(loan.getClientId());
        externalService.notifyKardexReturnLoanDelete(dto);

        return loanRepo.save(loan);
    }

    public List<Long> findClientDelayed(){
        return loanRepo.findClientIdsWithDelayedLoans();
    }

    public List<Loan> findActiveAndOnTimeLoans() {
        return loanRepo.findActiveAndOnTimeLoans();
    }

    public List<Loan> findActiveAndDelayedLoans() {
        return loanRepo.findActiveAndDelayedLoans();
    }

    public List<ToolRankingDto> findMostLoanedToolsWithDetails() {
        return loanRepo.findMostLoanedToolsWithDetails();
    }

    public Optional<Loan> getLoanById(Long id) {
        return loanRepo.findById(id);
    }
}
