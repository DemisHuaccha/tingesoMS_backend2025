package com.tingeso.tingesoMS_kardex.Dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor // Esto genera el constructor de 11 parámetros
public class CardexDto {
    private Long id; // Cambiado de long a Long
    private LocalDate moveDate;
    private String typeMove;
    private String description;
    private Integer amount;
    private Integer quantity;
    private String userEmail;
    private Long toolId;
    private Long loanId;
    private String clientRut;
    private Long clientId; // Este es el campo número 11
}
