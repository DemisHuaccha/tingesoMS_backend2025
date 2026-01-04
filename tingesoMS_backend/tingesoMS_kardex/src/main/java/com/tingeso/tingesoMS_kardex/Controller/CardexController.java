package com.tingeso.tingesoMS_kardex.Controller;

import com.tingeso.tingesoMS_kardex.Dtos.CardexDto;
import com.tingeso.tingesoMS_kardex.Service.CardexService;
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
    private CardexService cardexService;

    @PostMapping("/log")
    public ResponseEntity<Void> logMove(@RequestBody CardexDto logEntry) {
        cardexService.log(logEntry);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<CardexDto>> getAllCardex() {
        return ResponseEntity.ok(cardexService.findAllDto());
    }

    @GetMapping("/tool/{toolId}")
    public ResponseEntity<List<CardexDto>> getCardexTool(@PathVariable Long toolId) {
        return ResponseEntity.ok(cardexService.findCardexTool(toolId));
    }

    @PostMapping("/filter/date")
    public ResponseEntity<List<CardexDto>> filterDateRange(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
            @RequestParam(required = false) Long toolId) {
        
        return ResponseEntity.ok(cardexService.findForRangeDate(start, end, toolId));
    }
}
