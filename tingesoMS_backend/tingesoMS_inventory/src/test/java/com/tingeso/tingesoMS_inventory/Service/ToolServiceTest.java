package com.tingeso.tingesoMS_inventory.Service;

import com.tingeso.tingesoMS_inventory.Entities.Tool;
import com.tingeso.tingesoMS_inventory.Repository.ToolRepositorie;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ToolServiceTest {

    @Mock
    private ToolRepositorie toolRepo;

    @InjectMocks
    private ToolService toolService; // Note: Ensure logic class name matches (ToolServiceImpl or ToolService)

    @Test
    void save() {
        Tool tool = new Tool();
        tool.setName("Hammer");
        when(toolRepo.save(any(Tool.class))).thenReturn(tool);

        Tool result = toolService.save(tool);
        assertNotNull(result);
        assertEquals("Hammer", result.getName());
    }

    @Test
    void findById() {
        Tool tool = new Tool();
        tool.setIdTool(1L);
        when(toolRepo.findById(1L)).thenReturn(Optional.of(tool));

        Tool result = toolService.findById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getIdTool());
    }
    
    @Test
    void updateStatus() {
        Tool tool = new Tool();
        tool.setIdTool(1L);
        tool.setStatus(true); // Available
        
        when(toolRepo.findById(1L)).thenReturn(Optional.of(tool));
        
        toolService.updateStatus(1L, false, null, null);
        
        assertFalse(tool.getStatus());
        verify(toolRepo).save(tool);
    }
}
