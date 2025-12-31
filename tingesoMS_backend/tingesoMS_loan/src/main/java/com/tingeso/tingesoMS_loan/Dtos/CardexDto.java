package com.tingeso.tingesoMS_loan.Dtos;

import java.time.LocalDate;

public class CardexDto {
    private Long id;
    private LocalDate moveDate;
    private String typeMove;
    private String description;
    private Integer amount;
    private Integer quantity;
    private String userEmail;
    private Long toolId;
    private Long loanId;
    private String clientRut;
    
    public CardexDto() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getMoveDate() { return moveDate; }
    public void setMoveDate(LocalDate moveDate) { this.moveDate = moveDate; }

    public String getTypeMove() { return typeMove; }
    public void setTypeMove(String typeMove) { this.typeMove = typeMove; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Integer getAmount() { return amount; }
    public void setAmount(Integer amount) { this.amount = amount; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }

    public Long getToolId() { return toolId; }
    public void setToolId(Long toolId) { this.toolId = toolId; }

    public Long getLoanId() { return loanId; }
    public void setLoanId(Long loanId) { this.loanId = loanId; }

    public String getClientRut() { return clientRut; }
    public void setClientRut(String clientRut) { this.clientRut = clientRut; }
}
