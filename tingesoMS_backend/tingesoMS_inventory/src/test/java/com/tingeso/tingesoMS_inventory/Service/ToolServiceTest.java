package com.tingeso.tingesoMS_inventory.Service;

import com.tingeso.tingesoMS_inventory.Dtos.CreateToolDto;
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
    private ToolService toolService;

    @Test
    void save() {
        CreateToolDto dto = new CreateToolDto();
        dto.setName("Hammer");
        dto.setStock(1);
        
        Tool tool = new Tool();
        tool.setName("Hammer");
        
        when(toolRepo.save(any(Tool.class))).thenReturn(tool);

        Tool result = toolService.save(dto);
        assertNotNull(result);
        assertEquals("Hammer", result.getName());
    }
    
    @Test
    void saveMultiple() {
        CreateToolDto dto = new CreateToolDto();
        dto.setName("Nail");
        dto.setStock(3);
        
        Tool tool = new Tool();
        tool.setName("Nail");
        
        when(toolRepo.save(any(Tool.class))).thenReturn(tool);

        Tool result = toolService.save(dto);
        assertNotNull(result); // Returns last one
        verify(toolRepo, times(3)).save(any(Tool.class));
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
        
        verify(toolRepo).save(tool);
    }
    
    @Test
    void updateStatusTool() {
        Tool tool = new Tool();
        tool.setIdTool(1L);
        when(toolRepo.findById(1L)).thenReturn(Optional.of(tool));
        
        toolService.updateStatusTool(1L, false);
        verify(toolRepo).save(tool);
    }

    @Test
    void findAll() {
        Tool tool = new Tool();
        tool.setName("Drill");
        when(toolRepo.findAll()).thenReturn(java.util.List.of(tool));

        java.util.List<Tool> result = toolService.findAll();
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }
    
    @Test
    void findAllAvailable() {
        when(toolRepo.findByStatusTrue()).thenReturn(java.util.List.of(new Tool()));
        assertFalse(toolService.findAllAvalible().isEmpty());
    }
    
    @Test
    void findAllNotDelete() {
        when(toolRepo.findByDeleteStatusFalse()).thenReturn(java.util.List.of(new Tool()));
        assertFalse(toolService.findAllNotDelete().isEmpty());
    }
    
    @Test
    void groupTools() {
        when(toolRepo.groupTools()).thenReturn(java.util.List.of(new com.tingeso.tingesoMS_inventory.Dtos.GroupToolsDto("Cat", "Name", 1L)));
        assertFalse(toolService.groupTools().isEmpty());
    }
    
    @Test
    void getConditions() {
        assertFalse(toolService.getConditions().isEmpty());
    }
    
    @Test
    void filterTools() {
        Tool t = new Tool();
        t.setName("Hammer");
        when(toolRepo.findAll()).thenReturn(java.util.List.of(t));
        
        java.util.List<Tool> result = toolService.filterTools("Ham");
        assertEquals(1, result.size());
    }
    
    @Test
    void updateTool() {
        Tool t = new Tool();
        t.setIdTool(1L);
        when(toolRepo.findById(1L)).thenReturn(Optional.of(t));
        when(toolRepo.save(any(Tool.class))).thenReturn(t);
        
        CreateToolDto dto = new CreateToolDto();
        dto.setName("New");
        
        Tool result = toolService.updateTool(dto, 1L);
        assertNotNull(result);
    }
    
    @Test
    void underRepairTool() {
        Tool tool = new Tool();
        tool.setIdTool(1L);
        when(toolRepo.findById(1L)).thenReturn(Optional.of(tool));
        
        toolService.underRepairTool(1L, true);
        verify(toolRepo).save(tool);
    }
    
    @Test
    void deleteTool() {
        Tool tool = new Tool();
        tool.setIdTool(1L);
        when(toolRepo.findById(1L)).thenReturn(Optional.of(tool));
        
        toolService.deleteTool(1L, true);
        verify(toolRepo).save(tool);
    }
}
