package com.tingeso.tingesoMS_fee.Service;

import com.tingeso.tingesoMS_fee.Entities.ToolFee;
import com.tingeso.tingesoMS_fee.Repository.ToolFeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FeeServiceTest {

    @Mock
    private ToolFeeRepository repo;

    @InjectMocks
    private FeeService service;

    @Test
    void save() {
        ToolFee fee = new ToolFee();
        fee.setToolId(1L);
        fee.setLoanFee(1000);
        when(repo.save(any(ToolFee.class))).thenReturn(fee);
        
        ToolFee result = service.save(fee);
        assertEquals(1000, result.getLoanFee());
    }

    @Test
    void getFee() {
        ToolFee fee = new ToolFee();
        fee.setToolId(1L);
        when(repo.findById(1L)).thenReturn(Optional.of(fee));
        
        ToolFee result = service.getFee(1L);
        assertNotNull(result);
    }
}
