package com.tingeso.tingesoMS_fee.Controller;

import com.tingeso.tingesoMS_fee.Entities.ToolFee;
import com.tingeso.tingesoMS_fee.Service.FeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fee")
public class FeeController {

    @Autowired
    private FeeService service;

    @PostMapping("/tool/save")
    public ResponseEntity<ToolFee> saveToolFee(@RequestBody ToolFee fee) {
        return ResponseEntity.ok(service.save(fee));
    }

    @GetMapping("/tool/{id}")
    public ResponseEntity<ToolFee> getToolFee(@PathVariable Long id) {
        ToolFee f = service.getFee(id);
        if(f==null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(f);
    }
}
