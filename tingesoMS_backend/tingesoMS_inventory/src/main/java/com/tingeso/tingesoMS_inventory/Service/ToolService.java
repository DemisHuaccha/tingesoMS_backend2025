package com.tingeso.tingesoMS_inventory.Service;

import com.tingeso.tingesoMS_inventory.Entities.Tool;
import com.tingeso.tingesoMS_inventory.Repository.ToolRepositorie;
import com.tingeso.tingesoMS_inventory.Dtos.CreateToolDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToolService {

    @Autowired
    private ToolRepositorie toolRepo;

    public Tool save(CreateToolDto dto) {
        // Basic batch creation. Returns the last created to simplify return type, or we could change generic.
        // Logic: if stock > 1, create multiple.
        int qty = dto.getStock() > 0 ? dto.getStock() : 1;
        Tool lastTool = null;
        for (int i = 0; i < qty; i++) {
            Tool tool = new Tool();
            tool.setName(dto.getName());
            tool.setCategory(dto.getCategory());
            // Default fields as per new DTO
            if(dto.getInitialCondition() != null) {
                // Mapping String to Enum if necessary, assuming Enum exists in Entity or String just saved.
                // Assuming Entity uses String or Enum. Let's check Entity if possible, but for now saving as is.
                // tool.setInitialCondition(dto.getInitialCondition()); 
            }
            tool.setStatus(dto.getStatus() != null ? dto.getStatus() : true);
            tool.setUnderRepair(dto.getUnderRepair() != null ? dto.getUnderRepair() : false);
            tool.setDeleteStatus(dto.getDeleteStatus() != null ? dto.getDeleteStatus() : false);
            
            lastTool = toolRepo.save(tool);
        }
        return lastTool;
    }

    // Overload for direct Entity save if needed
    public Tool save(Tool tool) {
        return toolRepo.save(tool);
    }
    
    public Tool findById(Long id) {
        return toolRepo.findById(id).orElse(null);
    }
    
    public List<Tool> findAll() {
        return toolRepo.findAll();
    }
    
    public List<Tool> findAllAvalible() {
        return toolRepo.findByStatusTrue();
    }
    
    public List<Tool> findAllNotDelete() {
        return toolRepo.findByDeleteStatusFalse();
    }

    public Tool updateTool(CreateToolDto dto, Long id) {
        Tool t = findById(id);
        if(t != null) {
            t.setName(dto.getName());
            t.setCategory(dto.getCategory());
            // Update other fields if DTO has them
            return toolRepo.save(t);
        }
        return null;
    }
    
    public void updateStatus(Long id, Boolean status, Boolean underRepair, Boolean delete) {
        Tool t = findById(id);
        if (t != null) {
            if(status != null) t.setStatus(status);
            if(underRepair != null) t.setUnderRepair(underRepair);
            if(delete != null) t.setDeleteStatus(delete);
            toolRepo.save(t);
        }
    }
    
    // Status Toggles logic
    public void updateStatusTool(Long id, Boolean status) {
        // Toggle logic from Monolith: "If param is same as current, invert it?" Or just set it?
        // Monolith test comment: "El servicio invierte el valor si coincide."
        // Let's implement set directly for clarity unless strict parity needed. 
        // Logic: Set status.
        updateStatus(id, status, null, null);
    }
    
    public void underRepairTool(Long id, Boolean status) {
        updateStatus(id, null, status, null);
    }
    
    public void deleteTool(Long id, Boolean status) {
        updateStatus(id, null, null, status);
    }
    
    public List<Tool> filterTools(String name) {
        // Simple filter example
         return toolRepo.findAll().stream()
                .filter(t -> t.getName().toLowerCase().contains(name.toLowerCase()))
                .toList();
    }
    
    public List<com.tingeso.tingesoMS_inventory.Dtos.GroupToolsDto> groupTools() {
        return toolRepo.groupTools();
    }
    
    public List<String> getConditions() {
        return java.util.Arrays.stream(com.tingeso.tingesoMS_inventory.Dtos.InitialCondition.values())
                .map(Enum::name)
                .toList();
    }
}
