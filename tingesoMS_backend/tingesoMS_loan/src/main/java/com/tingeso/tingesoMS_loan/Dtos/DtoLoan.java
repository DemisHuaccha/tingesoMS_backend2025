package com.tingeso.tingesoMS_loan.Dtos;

import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDate;

@Data
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
