package com.tingeso.tingesoMS_loan.Dtos;

public class ToolDto {
    private Long idTool;
    private String name;
    private String category;
    private Integer loanFee;
    private Integer penaltyForDelay;
    private Integer replacementValue;
    private Integer damageValue;
    private Boolean status;
    private Boolean underRepair;
    private Boolean deleteStatus;

    public Long getIdTool() { return idTool; }
    public void setIdTool(Long idTool) { this.idTool = idTool; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public Integer getLoanFee() { return loanFee; }
    public void setLoanFee(Integer loanFee) { this.loanFee = loanFee; }

    public Integer getPenaltyForDelay() { return penaltyForDelay; }
    public void setPenaltyForDelay(Integer penaltyForDelay) { this.penaltyForDelay = penaltyForDelay; }

    public Integer getReplacementValue() { return replacementValue; }
    public void setReplacementValue(Integer replacementValue) { this.replacementValue = replacementValue; }

    public Integer getDamageValue() { return damageValue; }
    public void setDamageValue(Integer damageValue) { this.damageValue = damageValue; }

    public Boolean getStatus() { return status; }
    public void setStatus(Boolean status) { this.status = status; }

    public Boolean getUnderRepair() { return underRepair; }
    public void setUnderRepair(Boolean underRepair) { this.underRepair = underRepair; }

    public Boolean getDeleteStatus() { return deleteStatus; }
    public void setDeleteStatus(Boolean deleteStatus) { this.deleteStatus = deleteStatus; }
}
