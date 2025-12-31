package com.tingeso.tingesoMS_loan.Dtos;

import lombok.Data;

@Data
public class ToolFeeDto {
    private Long toolId;
    private Integer loanFee;
    private Integer penaltyForDelay;
    private Integer damageValue;
    private Integer replacementValue;
}
