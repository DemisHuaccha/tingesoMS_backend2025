package com.tingeso.tingesoMS_inventory.Controller;

import com.tingeso.tingesoMS_inventory.Entities.Tool;
import com.tingeso.tingesoMS_inventory.Service.ToolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class ToolController {

    @Autowired
    private ToolService toolService;

    // Use DTO for creation to support multiple quantity
    @PostMapping("/create")
    public ResponseEntity<Tool> createTool(@RequestBody com.tingeso.tingesoMS_inventory.Dtos.CreateToolDto dto) {
        return ResponseEntity.ok(toolService.save(dto));
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
    
    @GetMapping("/available")
    public ResponseEntity<List<Tool>> getAllAvailable() {
        return ResponseEntity.ok(toolService.findAllAvalible());
    }
    
    @GetMapping("/notDeleted")
    public ResponseEntity<List<Tool>> getAllNotDelete() {
        return ResponseEntity.ok(toolService.findAllNotDelete());
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<Tool> updateTool(@RequestBody com.tingeso.tingesoMS_inventory.Dtos.CreateToolDto dto, @PathVariable Long id) {
        return ResponseEntity.ok(toolService.updateTool(dto, id));
    }
    
    @PutMapping("/updateStatus")
    public ResponseEntity<Void> updateStatus(@RequestBody Tool tool) {
        toolService.updateStatus(tool.getIdTool(), tool.getStatus(), tool.getUnderRepair(), tool.getDeleteStatus());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/underRepair")
    public ResponseEntity<Tool> underRepairTool(@RequestBody com.tingeso.tingesoMS_inventory.Dtos.ToolStatusDto toolDto) {
        // Mapped to generic status update logic in service, but exposed as specific endpoint
        toolService.underRepairTool(toolDto.getIdTool(), toolDto.getUnderRepair());
        return ResponseEntity.ok(toolService.findById(toolDto.getIdTool()));
    }

    @PutMapping("/deleteTool")
    public ResponseEntity<Tool> deleteTool(@RequestBody com.tingeso.tingesoMS_inventory.Dtos.ToolStatusDto toolDto) {
         // Mapped to specific logic
         toolService.deleteTool(toolDto.getIdTool(), toolDto.getDeleteStatus());
         return ResponseEntity.ok(toolService.findById(toolDto.getIdTool()));
    }

    
    @PostMapping("/filter")
    public ResponseEntity<List<Tool>> filterTools(@RequestParam String name) {
        return ResponseEntity.ok(toolService.filterTools(name));
    }
    
    @GetMapping("/group")
    public ResponseEntity<List<com.tingeso.tingesoMS_inventory.Dtos.GroupToolsDto>> groupTools() {
        return ResponseEntity.ok(toolService.groupTools());
    }
    
    @GetMapping("/conditions")
    public ResponseEntity<List<String>> getConditions() {
        return ResponseEntity.ok(toolService.getConditions());
    }
}
