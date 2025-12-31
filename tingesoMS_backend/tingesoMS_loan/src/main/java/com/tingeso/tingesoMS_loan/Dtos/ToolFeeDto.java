package com.tingeso.tingesoMS_loan.Dtos;

public class ToolFeeDto {
    private Long toolId;
    private Integer loanFee;
    private Integer penaltyForDelay;
    private Integer damageValue;
    private Integer replacementValue;
    
    public ToolFeeDto() {}

    public Long getToolId() { return toolId; }
    public void setToolId(Long toolId) { this.toolId = toolId; }

    public Integer getLoanFee() { return loanFee; }
    public void setLoanFee(Integer loanFee) { this.loanFee = loanFee; }

    public Integer getPenaltyForDelay() { return penaltyForDelay; }
    public void setPenaltyForDelay(Integer penaltyForDelay) { this.penaltyForDelay = penaltyForDelay; }

    public Integer getDamageValue() { return damageValue; }
    public void setDamageValue(Integer damageValue) { this.damageValue = damageValue; }

    public Integer getReplacementValue() { return replacementValue; }
    public void setReplacementValue(Integer replacementValue) { this.replacementValue = replacementValue; }
}
