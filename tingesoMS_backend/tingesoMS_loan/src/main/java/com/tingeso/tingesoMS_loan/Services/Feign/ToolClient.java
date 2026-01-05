package com.tingeso.tingesoMS_loan.Services.Feign;

import com.tingeso.tingesoMS_loan.Dtos.ToolDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "tingesoMS_tool", url = "http://localhost:8082/api/tool")
public interface ToolClient {


    @GetMapping("/{id}")
    ToolDto getToolById(@PathVariable("id") Long id);

    @PutMapping("/updateStatus")
    void updateStatus(@RequestBody Object statusDto); 

}
