package com.tingeso.tingesoMS_kardex.Service;

import com.tingeso.tingesoMS_kardex.Dtos.CardexDto;
import com.tingeso.tingesoMS_kardex.Entities.Cardex;
import com.tingeso.tingesoMS_kardex.Repository.CardexRepositorie;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CardexServiceTest {

    @Mock
    private CardexRepositorie repo;

    @InjectMocks
    private CardexServiceImpl service;

    @Test
    void saveCardexLoan() {
        CardexDto dto = new CardexDto();
        dto.setUserEmail("loan@test.com");
        dto.setToolId(1L);
        dto.setClientRut("11.111.111-1");
        dto.setTypeMove("Create Loan"); // Monolith Expectation
        
        service.log(dto);
        verify(repo).save(argThat(c -> "Create Loan".equals(c.getTypeMove()) && "loan@test.com".equals(c.getUserEmail())));
    }

    @Test
    void saveCardexTool() {
        CardexDto dto = new CardexDto();
        dto.setUserEmail("tool@test.com");
        dto.setQuantity(5);
        dto.setTypeMove("Create Tool");
        
        service.log(dto);
        verify(repo).save(argThat(c -> "Create Tool".equals(c.getTypeMove()) && c.getQuantity() == 5));
    }

    @Test
    void saveCardexClient() {
        CardexDto dto = new CardexDto();
        dto.setUserEmail("client@test.com");
        dto.setClientRut("33.333.333-3");
        dto.setTypeMove("Create Client");
        
        service.log(dto);
        verify(repo).save(argThat(c -> "Create Client".equals(c.getTypeMove()) && "33.333.333-3".equals(c.getClientRut())));
    }

    @Test
    void saveCardexUpdateTool() {
        CardexDto dto = new CardexDto();
        dto.setUserEmail("updtool@test.com");
        dto.setTypeMove("Update Tool");
        
        service.log(dto);
        verify(repo).save(argThat(c -> "Update Tool".equals(c.getTypeMove())));
    }

    @Test
    void saveCardexUpdateStatusTool() {
        CardexDto dto = new CardexDto();
        dto.setUserEmail("status@test.com");
        dto.setTypeMove("Tool update status to: Available");
        
        service.log(dto);
        verify(repo).save(argThat(c -> c.getTypeMove().startsWith("Tool update status")));
    }

    @Test
    void saveCardexRepairTool() {
        CardexDto dto = new CardexDto();
        dto.setUserEmail("repair@test.com");
        dto.setTypeMove("Tool Repair status: In Repair");
        
        service.log(dto);
        verify(repo).save(argThat(c -> c.getTypeMove().startsWith("Tool Repair status")));
    }

    @Test
    void saveCardexDeleteTool() {
        CardexDto dto = new CardexDto();
        dto.setUserEmail("del@test.com");
        dto.setTypeMove("Tool Delete");
        
        service.log(dto);
        verify(repo).save(argThat(c -> "Tool Delete".equals(c.getTypeMove())));
    }

    @Test
    void saveCardexReturnLoan() {
        CardexDto dto = new CardexDto();
        dto.setUserEmail("return@test.com");
        dto.setTypeMove("Loan Finished");
        
        service.log(dto);
        verify(repo).save(argThat(c -> "Loan Finished".equals(c.getTypeMove())));
    }

    @Test
    void saveCardexReturnLoanDamage() {
        CardexDto dto = new CardexDto();
        dto.setUserEmail("damage@test.com");
        dto.setDescription("Loan returned damaged");
        dto.setTypeMove("Loan Finished");
        
        service.log(dto);
        verify(repo).save(argThat(c -> c.getDescription().contains("damaged")));
    }

    @Test
    void saveCardexReturnLoanDelete() {
        CardexDto dto = new CardexDto();
        dto.setUserEmail("reploan@test.com");
        dto.setDescription("Loan returned replaced");
        dto.setTypeMove("Loan Finished");
        
        service.log(dto);
        verify(repo).save(argThat(c -> c.getDescription().contains("replaced")));
    }

    @Test
    void findAllDto() {
        when(repo.findAllCardex()).thenReturn(List.of(new CardexDto()));
        List<CardexDto> result = service.findAllDto();
        assertFalse(result.isEmpty());
    }

    @Test
    void findForRangeDate() {
        when(repo.findCardexDateRange(any(), any())).thenReturn(List.of(new CardexDto()));
        List<CardexDto> result = service.findForRangeDate(LocalDate.now(), LocalDate.now(), null);
        assertFalse(result.isEmpty());
    }

    @Test
    void findCardexTool() {
        List<CardexDto> list = new ArrayList<>();
        list.add(new CardexDto());
        when(repo.findCardexByToolId(1L)).thenReturn(list);
        
        List<CardexDto> result = service.findCardexTool(1L);
        assertFalse(result.isEmpty());
    }
}
