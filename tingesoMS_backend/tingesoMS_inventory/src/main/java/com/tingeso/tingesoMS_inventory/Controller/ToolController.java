package com.tingeso.tingesoMS_inventory.Controller;

import com.tingeso.tingesoMS_inventory.Entities.Tool;
import com.tingeso.tingesoMS_inventory.Service.ToolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tool")
public class ToolController {

    @Autowired
    private ToolService toolService;

    @PostMapping("/create")
    public ResponseEntity<Tool> createTool(@RequestBody Tool tool) {
        return ResponseEntity.ok(toolService.save(tool));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tool> getTool(@PathVariable Long id) {
        Tool tool = toolService.findById(id);
        if (tool == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(tool);
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<Tool>> getAll() {
        return ResponseEntity.ok(toolService.findAll());
    }
    
    @PutMapping("/updateStatus")
    public ResponseEntity<Void> updateStatus(@RequestBody Tool tool) {
        // Helper DTO probably better, but reusing Entity for simplify.
        toolService.updateStatus(tool.getIdTool(), tool.getStatus(), tool.getUnderRepair(), tool.getDeleteStatus());
        return ResponseEntity.ok().build();
    }
}
