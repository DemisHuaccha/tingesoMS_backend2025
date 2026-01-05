package com.tingeso.tingesoMS_loan.Dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class ToolDto {
    private String name;
    private String description;
    private String category;
    private InitialCondition initialCondition;
    private Integer loanFee;
    private Integer penaltyForDelay;
    private Integer replacementValue;
    private Integer damageValue;
    private Boolean status;
    private Boolean underRepair;
    private Boolean deleteStatus;

    /*--------------*/
    private Integer quantity;
    private String email;
    private Long idTool;
}
