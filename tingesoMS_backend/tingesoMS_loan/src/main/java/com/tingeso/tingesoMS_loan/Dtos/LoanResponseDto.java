package com.tingeso.tingesoMS_loan.Dtos;

import java.time.LocalDate;

public class LoanResponseDto {
    private Long loanId;
    private LocalDate deliveryDate;
    private LocalDate returnDate;
    private Boolean loanStatus;
    private Boolean penalty;
    private Integer penaltyTotal;
    private String clientRut;
    private Long toolId;
    private Integer priceToPay;

    public LoanResponseDto(Long loanId, LocalDate deliveryDate, LocalDate returnDate, Boolean loanStatus,
                           Boolean penalty, Integer penaltyTotal, String clientRut, Long toolId, Integer priceToPay) {
        this.loanId = loanId;
        this.deliveryDate = deliveryDate;
        this.returnDate = returnDate;
        this.loanStatus = loanStatus;
        this.penalty = penalty;
        this.penaltyTotal = penaltyTotal;
        this.clientRut = clientRut;
        this.toolId = toolId;
        this.priceToPay = priceToPay;
    }
    
    public Long getLoanId() { return loanId; }
    public void setLoanId(Long loanId) { this.loanId = loanId; }
    public LocalDate getDeliveryDate() { return deliveryDate; }
    public void setDeliveryDate(LocalDate deliveryDate) { this.deliveryDate = deliveryDate; }
    public LocalDate getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }
    public Boolean getLoanStatus() { return loanStatus; }
    public void setLoanStatus(Boolean loanStatus) { this.loanStatus = loanStatus; }
    public Boolean getPenalty() { return penalty; }
    public void setPenalty(Boolean penalty) { this.penalty = penalty; }
    public Integer getPenaltyTotal() { return penaltyTotal; }
    public void setPenaltyTotal(Integer penaltyTotal) { this.penaltyTotal = penaltyTotal; }
    public String getClientRut() { return clientRut; }
    public void setClientRut(String clientRut) { this.clientRut = clientRut; }
    public Long getToolId() { return toolId; }
    public void setToolId(Long toolId) { this.toolId = toolId; }
    public Integer getPriceToPay() { return priceToPay; }
    public void setPriceToPay(Integer priceToPay) { this.priceToPay = priceToPay; }
}
