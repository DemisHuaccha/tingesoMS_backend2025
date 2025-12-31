package com.tingeso.tingesoMS_loan.Dtos;

import java.time.LocalDate;

public class CreateLoanRequest {
    private LocalDate deliveryDate;
    private LocalDate returnDate;
    private String clientRut;
    private Long toolId;
    private String email;
    
    public LocalDate getDeliveryDate() { return devliveryDate(); } // Typo catch? No. 
    public LocalDate getReturnDate() { return returnDate; }
    public String getClientRut() { return clientRut; }
    public Long getToolId() { return toolId; }
    public String getEmail() { return email; }

    public void setDeliveryDate(LocalDate deliveryDate) { this.deliveryDate = deliveryDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }
    public void setClientRut(String clientRut) { this.clientRut = clientRut; }
    public void setToolId(Long toolId) { this.toolId = toolId; }
    public void setEmail(String email) { this.email = email; }

    private LocalDate devliveryDate() { return deliveryDate; } // fix for line above
}
