package com.tingeso.tingesoMS_loan.Entities;

import jakarta.persistence.*;
// Removing Lombok to ensure compilation
// import lombok.AllArgsConstructor;
// import lombok.Getter;
// import lombok.NoArgsConstructor;
// import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "loan")
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLoan; // Renamed to match Service usage (getIdLoan)

    @Column(name = "deliveryDate")
    private LocalDate deliveryDate;

    @Column(name = "returnDate")
    private LocalDate returnDate;

    // References to other services
    @Column(name = "id_Customer")
    private Long clientId;

    @Column(name = "id_Tool")
    private Long toolId;

    // Snapshots for Reporting
    @Column(name = "client_rut")
    private String clientRut;

    @Column(name = "tool_name")
    private String toolName;
    
    @Column(name = "tool_category")
    private String toolCategory;
    
    @Column(name = "tool_loan_fee")
    private Integer toolLoanFee;

    //True es que esta activa
    //False es que ya fue completada(Es decir no se tiene en cuenta)
    @Column(name = "loan_status")
    private Boolean loanStatus;

    //Booleano que indica si hay atraso
    @Column(name= "penalty")
    private Boolean penalty;

    @Column(name = "penalty_Total")
    private Integer penaltyTotal;

    @Column(name = "priceToPay")
    private Integer priceToPay;
    
    public Loan() {}

    public Long getIdLoan() { return idLoan; }
    public void setIdLoan(Long idLoan) { this.idLoan = idLoan; }

    public LocalDate getDeliveryDate() { return deliveryDate; }
    public void setDeliveryDate(LocalDate deliveryDate) { this.deliveryDate = deliveryDate; }

    public LocalDate getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }

    public Long getClientId() { return clientId; }
    public void setClientId(Long clientId) { this.clientId = clientId; }

    public Long getToolId() { return toolId; }
    public void setToolId(Long toolId) { this.toolId = toolId; }

    public String getClientRut() { return clientRut; }
    public void setClientRut(String clientRut) { this.clientRut = clientRut; }

    public String getToolName() { return toolName; }
    public void setToolName(String toolName) { this.toolName = toolName; }

    public String getToolCategory() { return toolCategory; }
    public void setToolCategory(String toolCategory) { this.toolCategory = toolCategory; }

    public Integer getToolLoanFee() { return toolLoanFee; }
    public void setToolLoanFee(Integer toolLoanFee) { this.toolLoanFee = toolLoanFee; }

    public Boolean getLoanStatus() { return loanStatus; }
    public void setLoanStatus(Boolean loanStatus) { this.loanStatus = loanStatus; }

    public Boolean getPenalty() { return penalty; }
    public void setPenalty(Boolean penalty) { this.penalty = penalty; }

    public Integer getPenaltyTotal() { return penaltyTotal; }
    public void setPenaltyTotal(Integer penaltyTotal) { this.penaltyTotal = penaltyTotal; }

    public Integer getPriceToPay() { return priceToPay; }
    public void setPriceToPay(Integer priceToPay) { this.priceToPay = priceToPay; }
}
