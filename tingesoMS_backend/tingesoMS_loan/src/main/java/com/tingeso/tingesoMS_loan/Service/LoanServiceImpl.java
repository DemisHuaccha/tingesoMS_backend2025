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

@Service
@Transactional
public class LoanServiceImpl implements LoanService {

    @Autowired
    private LoanRepositorie loanRepo;
    
    @Autowired
    private ExternalServiceProvider externalService;

    @Override
    public Boolean isToolAvailableForClient(Long clientId, String toolName, String toolCategory, Integer loanFee) {
        return loanRepo.clientHasNoMatchingLoan(clientId, toolName, toolCategory, loanFee);
    }

    @Override
    public Loan createLoan(String clientRut, Long toolId, LocalDate deliveryDate, LocalDate expectedReturnDate) {

        if (clientRut == null || toolId == null) {
            throw new IllegalArgumentException("clientRut and toolId cannot be null");
        }

        ClientDto client = externalService.getClientByRut(clientRut);
        if (client == null) {
            throw new IllegalArgumentException("Client not found");
        }

        ToolDto tool = externalService.getToolById(toolId);
        if (tool == null) {
            throw new IllegalArgumentException("Tool not found");
        }
        
        // M4: GET FEE
        ToolFeeDto fee = externalService.getToolFee(toolId);
        Integer loanFee = fee != null ? fee.getLoanFee() : 0;
        Integer penaltyForDelay = fee != null ? fee.getPenaltyForDelay() : 0;

        if (Boolean.FALSE.equals(tool.getStatus()) || Boolean.TRUE.equals(tool.getDeleteStatus()) || Boolean.TRUE.equals(tool.getUnderRepair())){
            throw new IllegalStateException("Tool is not available for loan");
        }

        List<Loan> defaulter = loanRepo.findByClientIdAndLoanStatusTrueAndPenaltyTrue(client.getIdCustomer());
        if (!defaulter.isEmpty()) {
            throw new IllegalStateException("Client with outstanding fines ");
        }

        if (isToolAvailableForClient(client.getIdCustomer(), tool.getName(), tool.getCategory(), loanFee)){
            throw new IllegalStateException("Client has a loan with same category, name and loan Fee tool ");
        }

        List<Loan> activeLoans = loanRepo.findByClientIdAndLoanStatusTrue(client.getIdCustomer());
        if (activeLoans.size() >= 5) {
            throw new IllegalStateException("Client has reached the maximum of 5 active loans");
        }

        Loan loan = new Loan();
        loan.setClientId(client.getIdCustomer());
        loan.setToolId(tool.getIdTool());
        loan.setDeliveryDate(deliveryDate);
        loan.setReturnDate(expectedReturnDate);
        loan.setLoanStatus(true);
        loan.setPenalty(false);
        loan.setPenaltyTotal(0);
        
        loan.setClientRut(client.getRut());
        loan.setToolName(tool.getName());
        loan.setToolCategory(tool.getCategory());
        loan.setToolLoanFee(loanFee); // Stored snapshot

        loanRepo.save(loan);

        ToolStatusDto toolStatus = new ToolStatusDto();
        toolStatus.setIdTool(tool.getIdTool());
        toolStatus.setStatus(true); 
        externalService.updateToolStatus(toolStatus);
        
        // M5: Log to Kardex
        CardexDto log = new CardexDto();
        log.setMoveDate(LocalDate.now());
        log.setTypeMove("LOAN_CREATED");
        log.setDescription("Loan created for tool " + tool.getName());
        log.setAmount(loanFee);
        log.setQuantity(1);
        log.setUserEmail("system"); // Or extract from context if auth passed
        log.setToolId(tool.getIdTool());
        log.setLoanId(loan.getIdLoan());
        log.setClientRut(client.getRut());
        externalService.logCardex(log);

        return loan;
    }

    @Override
    public Loan returnLoan(Long loanId, LocalDate actualReturnDate) {
        Loan loan = loanRepo.findById(loanId)
                .orElseThrow(() -> new IllegalArgumentException("Loan not found"));

        if (!loan.getLoanStatus()) {
            throw new IllegalStateException("Loan already returned");
        }

        loan.setLoanStatus(false);
        
        // Try fetch fee again for penalty or use snapshot? Entity doesn't have penalty snapshot.
        // Fetch fee.
        ToolFeeDto fee = externalService.getToolFee(loan.getToolId());
        int penaltyVal = fee != null ? fee.getPenaltyForDelay() : 0;

        long daysLate = ChronoUnit.DAYS.between(loan.getReturnDate(), actualReturnDate);
        if (daysLate > 0) {
            int multa = (int) (daysLate * penaltyVal);
            loan.setPenalty(true);
            loan.setPenaltyTotal(multa);
        } else {
            loan.setPenalty(false);
            loan.setPenaltyTotal(0);
        }

        ToolStatusDto toolStatus = new ToolStatusDto();
        toolStatus.setIdTool(loan.getToolId());
        toolStatus.setStatus(false); 
        externalService.updateToolStatus(toolStatus);

        loanRepo.save(loan);
        externalService.updateClientStatus(loan.getClientId());
        
        // M5 Log
         CardexDto log = new CardexDto();
        log.setMoveDate(LocalDate.now());
        log.setTypeMove("LOAN_RETURNED");
        log.setDescription("Loan returned");
        log.setAmount(loan.getPriceToPay());
        log.setQuantity(1);
        log.setUserEmail("system");
        log.setToolId(loan.getToolId());
        log.setLoanId(loan.getIdLoan());
        log.setClientRut(loan.getClientRut());
        externalService.logCardex(log);
        
        return loanRepo.save(loan);
    }
    
    @Override
    public Loan returnLoanDamageTool(Long loanId, LocalDate actualReturnDate) {
         Loan loan = loanRepo.findById(loanId)
                .orElseThrow(() -> new IllegalArgumentException("Loan not found"));
        if (!loan.getLoanStatus()) throw new IllegalStateException("Loan already returned");
        loan.setLoanStatus(false);
        
        ToolFeeDto fee = externalService.getToolFee(loan.getToolId());
        int penaltyForDelay = fee!=null ? fee.getPenaltyForDelay() : 0;
        int damageValue = fee!=null ? fee.getDamageValue() : 0;

        long daysLate = ChronoUnit.DAYS.between(loan.getReturnDate(), actualReturnDate);
        int delayPenalty = (daysLate > 0) ? (int) (daysLate * penaltyForDelay) : 0;
        int totalPenalty = delayPenalty + damageValue;

        loan.setPenalty(true);
        loan.setPenaltyTotal(totalPenalty);
        
        ToolStatusDto toolStatus = new ToolStatusDto();
        toolStatus.setIdTool(loan.getToolId());
        toolStatus.setStatus(false); 
        externalService.updateToolStatus(toolStatus);

        int currentPrice = loan.getPriceToPay() != null ? loan.getPriceToPay() : 0;
        loan.setPriceToPay(currentPrice + damageValue);
        
        externalService.updateClientStatus(loan.getClientId());
        return loanRepo.save(loan);
    }

    @Override
    public Loan returnLoanDeleteTool(Long loanId, LocalDate actualReturnDate) {
         Loan loan = loanRepo.findById(loanId)
                .orElseThrow(() -> new IllegalArgumentException("Loan not found"));
        if (!loan.getLoanStatus()) throw new IllegalStateException("Loan already returned");
        loan.setLoanStatus(false);
        
        ToolFeeDto fee = externalService.getToolFee(loan.getToolId());
        int penaltyForDelay = fee!=null ? fee.getPenaltyForDelay() : 0;
        int replacementValue = fee!=null ? fee.getReplacementValue() : 0;

        long daysLate = ChronoUnit.DAYS.between(loan.getReturnDate(), actualReturnDate);
        int delayPenalty = (daysLate > 0) ? (int) (daysLate * penaltyForDelay) : 0;
        int totalPenalty = delayPenalty + replacementValue;

        loan.setPenalty(true);
        loan.setPenaltyTotal(totalPenalty);
        
        ToolStatusDto toolStatus = new ToolStatusDto();
        toolStatus.setIdTool(loan.getToolId());
        toolStatus.setStatus(false); 
        externalService.updateToolStatus(toolStatus);

        int currentPrice = loan.getPriceToPay() != null ? loan.getPriceToPay() : 0;
        loan.setPriceToPay(currentPrice + replacementValue);
        
        externalService.updateClientStatus(loan.getClientId());
        return loanRepo.save(loan);
    }

    @Override
    public List<LoanResponseDto> findAll() {
        return loanRepo.findAllWithClientAndToolIds();
    }

    @Override
    public List<Long> findClientDelayed(){
        return loanRepo.findClientIdsWithDelayedLoans();
    }
    
    @Override
    public List<Loan> findActiveAndOnTimeLoans() {
        return loanRepo.findActiveAndOnTimeLoans();
    }

    @Override
    public List<Loan> findActiveAndDelayedLoans() {
        return loanRepo.findActiveAndDelayedLoans();
    }

    @Override
    public List<ToolRankingDto> findMostLoanedToolsWithDetails() {
        return loanRepo.findMostLoanedToolsWithDetails();
    }
}
