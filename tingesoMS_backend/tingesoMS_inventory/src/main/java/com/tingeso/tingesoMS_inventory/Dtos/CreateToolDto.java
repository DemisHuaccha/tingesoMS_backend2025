package com.tingeso.tingesoMS_inventory.Dtos;

import lombok.Data;

@Data
public class CreateToolDto {
    private Long idTool;
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

}
