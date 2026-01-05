package com.tingeso.tingesoMS_inventory.Dtos;

import lombok.Data;
import java.time.LocalDate;

@Data
public class CardexDto {
    private String typeMove;
    private LocalDate moveDate;
    private String description;
    private Integer amount;
    private Integer quantity;
    private String userEmail;
    private Long toolId;
    private Long loanId;
    private String clientRut;
}
