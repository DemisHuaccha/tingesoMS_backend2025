package com.tingeso.tingesoMS_loan.Service;

import com.tingeso.tingesoMS_loan.Dtos.*;
import com.tingeso.tingesoMS_loan.Entities.Loan;
import com.tingeso.tingesoMS_loan.Repository.LoanRepositorie;
import com.tingeso.tingesoMS_loan.Services.Providers.ExternalServiceProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoanServiceTest {

    @Mock
    private LoanRepositorie loanRepo;
    
    @Mock
    private ExternalServiceProvider externalService;

    @InjectMocks
    private LoanServiceImpl loanService;

    @Test
    void createLoan_Success() {
        String rut = "11.111.111-1";
        Long toolId = 1L;
        LocalDate now = LocalDate.now();
        
        // Mock Client
        ClientDto client = new ClientDto();
        client.setIdCustomer(1L);
        client.setRut(rut);
        when(externalService.getClientByRut(rut)).thenReturn(client);
        
        // Mock Tool
        ToolDto tool = new ToolDto();
        tool.setIdTool(toolId);
        tool.setName("Drill");
        tool.setCategory("Power");
        tool.setStatus(true); // Available
        // tool.setLoanFee(500); // Removed as it is not in ToolDto
        when(externalService.getToolById(toolId)).thenReturn(tool);
        
        // Mock Fee
        ToolFeeDto fee = new ToolFeeDto();
        fee.setLoanFee(500);
        fee.setPenaltyForDelay(100);
        when(externalService.getToolFee(toolId)).thenReturn(fee);
        
        // Mock Checks
        when(loanRepo.findByClientIdAndLoanStatusTrueAndPenaltyTrue(1L)).thenReturn(new ArrayList<>());
        when(loanRepo.clientHasNoMatchingLoan(any(), any(), any(), any())).thenReturn(false); // "HasNoMatching" returns false if has? No, usually true means "Yes, has no matching". Wait. method name is "clientHasNoMatchingLoan". If existing logic returns true if CLEAN, then I mock TRUE.
        // Let's check logic:
        // if (isToolAvailableForClient(...)) throw exception.
        // Logic in Impl: isToolAvailableForClient Calls "clientHasNoMatchingLoan".
        // If repo returns TRUE (has matching loan), we fail? 
        // Wait, typical naming. "clientHasNoMatchingLoan" -> If true, he has NO matching loan. So it's safe.
        // Impl: if (isToolAvailableForClient(...)) throw ...
        // Wait: Impl says "if (isToolAvailableForClient(...)) { throw ... }"
        // This implies if method returns TRUE, verify fails.
        // So method must mean "HasMatchingLoan". 
        // Let's assume repo method returns TRUE if he HAS a matching loan.
        when(loanRepo.clientHasNoMatchingLoan(anyLong(), anyString(), anyString(), anyInt())).thenReturn(false); 

        when(loanRepo.findByClientIdAndLoanStatusTrue(1L)).thenReturn(new ArrayList<>());
        
        Loan savedLoan = new Loan();
        when(loanRepo.save(any(Loan.class))).thenReturn(savedLoan);

        Loan result = loanService.createLoan(rut, toolId, now, now.plusDays(7));
        
        assertNotNull(result);
        verify(externalService).updateToolStatus(any(ToolStatusDto.class));
        verify(externalService).logCardex(any(CardexDto.class));
    }

    @Test
    void returnLoan_Success() {
        Long loanId = 1L;
        LocalDate now = LocalDate.now();
        
        Loan loan = new Loan();
        loan.setIdLoan(loanId);
        loan.setLoanStatus(true);
        loan.setReturnDate(now.minusDays(1)); // Returned 1 day *AFTER* Expected? No, here expected is now-1. So returned today = 1 day late.
        loan.setToolId(10L);
        loan.setClientId(5L);
        
        when(loanRepo.findById(loanId)).thenReturn(Optional.of(loan));
        
        ToolFeeDto fee = new ToolFeeDto();
        fee.setPenaltyForDelay(100);
        when(externalService.getToolFee(10L)).thenReturn(fee);
        
        when(loanRepo.save(any(Loan.class))).thenReturn(loan);
        
        Loan result = loanService.returnLoan(loanId, now);
        
        // logic: late by 1 day. now - (now-1) = 1 day.
        // Penalty = 1 * 100 = 100.
        assertTrue(result.getPenalty());
        assertEquals(100, result.getPenaltyTotal());
        
        verify(externalService).updateToolStatus(any());
        verify(externalService).updateClientStatus(5L);
        verify(externalService).logCardex(any());
    }
}
