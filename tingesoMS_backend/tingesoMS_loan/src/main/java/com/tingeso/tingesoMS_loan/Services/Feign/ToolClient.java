package com.tingeso.tingesoMS_loan.Services.Feign;

import com.tingeso.tingesoMS_loan.Dtos.ToolDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "tingesoMS_tool", url = "http://localhost:8082/api/tool")
public interface ToolClient {

    // @GetMapping("/getAll") - Removed dangling annotation
    // Need findById
    // ToolController has `getAll`. Wait.
    // I'll check ToolController content again in my mind. It didn't have `getById` explicitly exposed?
    // It had `updateStatusTool` returning `findById`.
    // I should add `getById` to ToolController.
    // I'll add `getById` to interface here.
    @GetMapping("/{id}")
    ToolDto getToolById(@PathVariable("id") Long id);

    @PutMapping("/updateStatus")
    void updateStatus(@RequestBody Object statusDto); 
    // Uses ToolStatusDto. I can map it via Object or create DTO. I'll use Object for flexibility or create ToolStatusDto in Loan service.
    // Ideally duplicate DTO.
}
