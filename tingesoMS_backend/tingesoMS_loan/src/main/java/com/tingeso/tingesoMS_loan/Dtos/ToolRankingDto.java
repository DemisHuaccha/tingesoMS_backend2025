package com.tingeso.tingesoMS_loan.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor // Genera el constructor de 4 campos que JPA necesita
public class ToolRankingDto {
    private String name;
    private String category;
    private Integer loanFee;
    private Long quantity; // Usa Long porque COUNT devuelve Long
}
