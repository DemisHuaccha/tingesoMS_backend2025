package com.tingeso.tingesoMS_report.Dtos;

import lombok.Data;
import java.time.LocalDate;

@Data
public class LoanDto {
    private Long idLoan;
    private LocalDate deliveryDate;
    private LocalDate returnDate;
    private String clientRut;
    private String toolName;
    private Long toolId;
    private String toolCategory;
    private Integer toolLoanFee;
    private Boolean loanStatus;
    private Boolean penalty;
    private Integer penaltyTotal;
    private Integer priceToPay;
}
