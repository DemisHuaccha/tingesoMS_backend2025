package com.tingeso.tingesoMS_fee.Controller;

import com.tingeso.tingesoMS_fee.Entities.DtoTool;
import com.tingeso.tingesoMS_fee.Service.FeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/fee")
public class FeeController {

    @Autowired
    private FeeService feeService;

    // Endpoint for Frontend to call
    @PostMapping("/tool/update")
    public ResponseEntity<?> updateFee(@RequestBody DtoTool feePayload) {
        feeService.updateFee(feePayload);
        return ResponseEntity.ok().build();
    }
}
