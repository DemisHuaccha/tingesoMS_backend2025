package com.tingeso.tingesoMS_fee.Service;

import com.tingeso.tingesoMS_fee.Entities.DtoTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeeService {
    
    @Autowired
    private com.tingeso.tingesoMS_fee.Services.Providers.ExternalServiceProvider externalService;

    // "Proxy" method to handle fee updates
    public void updateFee(DtoTool feePayload) {
        externalService.updateToolInInventory(feePayload);
    }
}
