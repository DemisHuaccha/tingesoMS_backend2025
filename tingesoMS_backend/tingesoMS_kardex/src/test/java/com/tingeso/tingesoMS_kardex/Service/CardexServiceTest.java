package com.tingeso.tingesoMS_kardex.Service;

import com.tingeso.tingesoMS_kardex.Dtos.CardexDto;
import com.tingeso.tingesoMS_kardex.Entities.Cardex;
import com.tingeso.tingesoMS_kardex.Repository.CardexRepositorie;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CardexServiceTest {

    @Mock
    private CardexRepositorie repo;

    @InjectMocks
    private CardexServiceImpl service;

    @Test
    void log() {
        CardexDto dto = new CardexDto();
        dto.setMoveDate(LocalDate.now());
        dto.setTypeMove("TEST");
        dto.setDescription("Test Description");
        dto.setQuantity(1);
        
        service.log(dto);
        
        verify(repo).save(any(Cardex.class));
    }

    @Test
    void findCardexTool() {
        List<CardexDto> list = new ArrayList<>();
        list.add(new CardexDto());
        when(repo.findCardexByToolId(1L)).thenReturn(list);
        
        List<CardexDto> result = service.findCardexTool(1L);
        assertFalse(result.isEmpty());
    }
}
