package com.tingeso.tingesoMS_kardex.Dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DtoTime {
    private LocalDate start;
    private LocalDate end;
    private Long idTool;
}
