package com.tingeso.tingesoMS_fee.Entities;

import lombok.Data;

@Data
public class DtoTool {
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
