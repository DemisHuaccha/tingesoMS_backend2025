package com.tingeso.tingesoMS_loan.Dtos;

import lombok.Data;
import java.time.LocalDate;

@Data
public class LoanResponseDto {
    private Long idLoan;
    private LocalDate deliveryDate;
    private LocalDate returnDate;
    private Long clientId;
    private Long toolId;
    private String clientRut;
    private String toolName;
    private String toolCategory;
    private Integer toolLoanFee;
    private Boolean loanStatus;
    private Boolean penalty;
    private Integer penaltyTotal;
    private Integer priceToPay;
}
