package com.tingeso.tingesoMS_kardex.Controller;

import com.tingeso.tingesoMS_kardex.Dtos.*;
import com.tingeso.tingesoMS_kardex.Service.CardexService;
import com.tingeso.tingesoMS_kardex.Service.CardexServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/kardex")
public class CardexController {

    @Autowired
    private CardexServiceImpl cardexService;

    @PostMapping("/log")
    public ResponseEntity<Void> logMove(@RequestBody CardexDto logEntry) {
        cardexService.log(logEntry);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/logRaw")
    public ResponseEntity<Void> logMoveRaw(@RequestBody CardexDto logEntry) {
        cardexService.log(logEntry);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/getForTime")
    public ResponseEntity<List<CardexDto>> getForTime(@RequestBody DtoTime times){
        List<CardexDto> cardexList= cardexService.findForRangeDate(times.getStart(),times.getEnd(), times.getIdTool());
        return ResponseEntity.ok(cardexList);
    }
    @PostMapping("/getCardexTool")
    public ResponseEntity<List<CardexDto>> getCardexTool(@RequestBody CardexDto cardexDto){
        List<CardexDto> cardexList= cardexService.findCardexTool(cardexDto.getToolId());
        return ResponseEntity.ok(cardexList);
    }

    @PostMapping("/filter/date")
    public ResponseEntity<List<CardexDto>> filterDateRange(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
            @RequestParam(required = false) Long toolId) {
        
        return ResponseEntity.ok(cardexService.findForRangeDate(start, end, toolId));
    }

    @GetMapping("/getAllCardex")
    public ResponseEntity<List<CardexDto>> getAll(){
        List<CardexDto> cardexDtoList= cardexService.findAllDto();
        return ResponseEntity.ok(cardexDtoList);
    }


    /*-----------------------------------------*/

    @PostMapping("/saveLoan")
    public ResponseEntity<Void> saveCardexLoan(@RequestBody DtoLoan loan) {
        cardexService.saveCardexLoan(loan);
        return ResponseEntity.ok().build();
    }

    /* 2. saveCardexTool */
    @PostMapping("/saveTool")
    public ResponseEntity<Void> saveCardexTool(@RequestBody CreateToolDto request) {
        cardexService.saveCardexTool(request);
        return ResponseEntity.ok().build();
    }

    /* 3. saveCardexClient */
    @PostMapping("/saveClient")
    public ResponseEntity<Void> saveCardexClient(@RequestBody DtoClient request) {
        cardexService.saveCardexClient(request);
        return ResponseEntity.ok().build();
    }

    /* 4. saveCardexUpdateTool */
    @PostMapping("/saveUpdateTool")
    public ResponseEntity<Void> saveCardexUpdateTool(@RequestBody CreateToolDto request) {
        cardexService.saveCardexUpdateTool(request);
        return ResponseEntity.ok().build();
    }

    /* 5. saveCardexUpdateStatusTool */
    @PostMapping("/saveUpdateStatusTool")
    public ResponseEntity<Void> saveCardexUpdateStatusTool(@RequestBody ToolStatusDto request) {
        cardexService.saveCardexUpdateStatusTool(request);
        return ResponseEntity.ok().build();
    }

    /* 6. saveCardexRepairTool */
    @PostMapping("/saveRepairTool")
    public ResponseEntity<Void> saveCardexRepairTool(@RequestBody ToolStatusDto request) {
        cardexService.saveCardexRepairTool(request);
        return ResponseEntity.ok().build();
    }

    /* 7. saveCardexDeleteTool */
    @PostMapping("/saveDeleteTool")
    public ResponseEntity<Void> saveCardexDeleteTool(@RequestBody ToolStatusDto request) {
        cardexService.saveCardexDeleteTool(request);
        return ResponseEntity.ok().build();
    }

    /* 8. saveCardexReturnLoan */
    @PostMapping("/saveReturnLoan")
    public ResponseEntity<Void> saveCardexReturnLoan(@RequestBody ReturnLoanDto loanDto) {
        cardexService.saveCardexReturnLoan(loanDto);
        return ResponseEntity.ok().build();
    }

    /* 9. saveCardexReturnLoanDamage */
    @PostMapping("/saveReturnLoanDamage")
    public ResponseEntity<Void> saveCardexReturnLoanDamage(@RequestBody ReturnLoanDto loanDto) {
        cardexService.saveCardexReturnLoanDamage(loanDto);
        return ResponseEntity.ok().build();
    }

    /* 10. saveCardexReturnLoanDelete */
    @PostMapping("/saveReturnLoanDelete")
    public ResponseEntity<Void> saveCardexReturnLoanDelete(@RequestBody ReturnLoanDto loanDto) {
        cardexService.saveCardexReturnLoanDelete(loanDto);
        return ResponseEntity.ok().build();
    }


}
