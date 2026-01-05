package com.tingeso.tingesoMS_loan.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
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

    public LoanResponseDto(Long idLoan, LocalDate deliveryDate, LocalDate returnDate,
                           Boolean loanStatus, Boolean penalty, Integer penaltyTotal,
                           String clientRut, Long toolId, Integer priceToPay) {
        this.idLoan = idLoan;
        this.deliveryDate = deliveryDate;
        this.returnDate = returnDate;
        this.loanStatus = loanStatus;
        this.penalty = penalty;
        this.penaltyTotal = penaltyTotal;
        this.clientRut = clientRut;
        this.toolId = toolId;
        this.priceToPay = priceToPay;
    }
}
