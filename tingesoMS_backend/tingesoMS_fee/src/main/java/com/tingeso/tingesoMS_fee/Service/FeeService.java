package com.tingeso.tingesoMS_fee.Service;

import com.tingeso.tingesoMS_fee.Entities.ToolFee;
import com.tingeso.tingesoMS_fee.Repository.ToolFeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeeService {
    @Autowired
    private ToolFeeRepository repo;

    public ToolFee save(ToolFee fee) {
        return repo.save(fee);
    }
    
    public ToolFee getFee(Long toolId) {
        return repo.findById(toolId).orElse(null);
    }
}
