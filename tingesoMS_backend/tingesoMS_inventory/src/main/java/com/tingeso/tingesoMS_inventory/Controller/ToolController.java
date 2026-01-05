package com.tingeso.tingesoMS_inventory.Controller;

import com.tingeso.tingesoMS_inventory.Dtos.CreateToolDto;
import com.tingeso.tingesoMS_inventory.Dtos.GroupToolsDto;
import com.tingeso.tingesoMS_inventory.Dtos.ToolRankingDto;
import com.tingeso.tingesoMS_inventory.Dtos.ToolStatusDto;
import com.tingeso.tingesoMS_inventory.Entities.Tool;
import com.tingeso.tingesoMS_inventory.Service.ToolService;
import com.tingeso.tingesoMS_inventory.Services.Providers.ExternalServiceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class ToolController {

    @Autowired
    private ToolService toolService;
    @Autowired
    private ExternalServiceProvider externalService;

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
    
    @PutMapping("/update")
    public ResponseEntity<Tool> updateTool(@RequestBody com.tingeso.tingesoMS_inventory.Dtos.CreateToolDto dto) {
        externalService.notifyKardexUpdateTool(dto);
        return ResponseEntity.ok(toolService.updateTool(dto));
    }
    
    @PutMapping("/updateStatus")
    public ResponseEntity<Void> updateStatus(@RequestBody ToolStatusDto toolDto) {
        externalService.notifyKardexUpdateStatusTool(toolDto);
        toolService.updateStatusTool(toolDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/underRepair")
    public ResponseEntity<Tool> underRepairTool(@RequestBody CreateToolDto toolDto) {
        toolService.underRepairTool(toolDto);
        return ResponseEntity.ok(toolService.findById(toolDto.getIdTool()));
    }

    @PutMapping("/deleteTool")
    public ResponseEntity<Tool> deleteTool(@RequestBody CreateToolDto toolDto) {
         toolService.deleteTool(toolDto);
         externalService.notifyKardexDeleteTool(toolDto);
         return ResponseEntity.ok(toolService.findById(toolDto.getIdTool()));
    }

    
    @PostMapping("/filter")
    public List<Tool> filterTools(@RequestBody ToolRankingDto toolDto){
        return toolService.filterTools(toolDto);
    }
    
    @GetMapping("/group")
    public ResponseEntity<List<GroupToolsDto>> groupTools() {
        return ResponseEntity.ok(toolService.groupTools());
    }
    
    @GetMapping("/countAvailable")
    public ResponseEntity<Integer> countAvailable(@RequestParam CreateToolDto dto) {
        return ResponseEntity.ok(toolService.countAvailable(dto.getName(), dto.getCategory(), dto.getLoanFee()));
    }
    
    @GetMapping("/conditions")
    public ResponseEntity<List<String>> getConditions() {
        return ResponseEntity.ok(toolService.getConditions());
    }
}
