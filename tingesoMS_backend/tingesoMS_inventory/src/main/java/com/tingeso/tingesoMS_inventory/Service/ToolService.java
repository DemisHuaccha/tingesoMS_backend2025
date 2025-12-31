package com.tingeso.tingesoMS_inventory.Service;

import com.tingeso.tingesoMS_inventory.Entities.Tool;
import com.tingeso.tingesoMS_inventory.Repository.ToolRepositorie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToolService {

    @Autowired
    private ToolRepositorie toolRepo;

    public Tool save(Tool tool) {
        // Validation logic? RF1.1 says "Name, Cat, InitState".
        // Prices? M1 creates Tool ID. Admin enters prices later? Or M1 calls M4?
        // Let's assume M1 just saves physical info.
        return toolRepo.save(tool);
    }
    
    public Tool findById(Long id) {
        return toolRepo.findById(id).orElse(null);
    }
    
    public List<Tool> findAll() {
        return toolRepo.findAll();
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
}
