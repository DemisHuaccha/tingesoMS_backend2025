package com.tingeso.tingesoMS_kardex.Dtos;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoLoan {
    private Long idLoan;
    private LocalDate deliveryDate;
    private LocalDate returnDate;
    private Long clientId;
    private Long toolId;
    private String clientRut;
    private String toolName;
    private String toolCategory;
    private Integer toolLoanFee;
    private Integer penaltyForDelay;
    private Boolean loanStatus;
    private Boolean penalty;
    private Integer penaltyTotal;
    private Integer priceToPay;
    private String email;

}
