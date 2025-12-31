package com.tingeso.tingesoMS_kardex.Service;

import com.tingeso.tingesoMS_kardex.Dtos.CardexDto;
import java.time.LocalDate;
import java.util.List;

public interface CardexService {

    void log(CardexDto logEntry);
    
    List<CardexDto> findAllDto();
    List<CardexDto> findForRangeDate(LocalDate start, LocalDate end, Long toolId);
    List<CardexDto> findCardexTool(Long toolId);
}
