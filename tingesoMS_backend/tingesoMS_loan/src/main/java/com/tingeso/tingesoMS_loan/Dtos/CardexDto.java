package com.tingeso.tingesoMS_loan.Dtos;

import lombok.Data;
import java.time.LocalDate;

@Data
public class CardexDto {
    private LocalDate moveDate;
    private String typeMove;
    private String description;
    private Integer amount;
    private Integer quantity;
    private String userEmail;
    private Long toolId;
    private Long loanId;
    private String clientRut;
}
