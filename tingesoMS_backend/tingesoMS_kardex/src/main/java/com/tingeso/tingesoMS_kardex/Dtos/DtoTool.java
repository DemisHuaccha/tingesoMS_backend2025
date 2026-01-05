package com.tingeso.tingesoMS_kardex.Dtos;

import lombok.Data;

@Data
public class DtoTool {
    private Long idTool;

    private String name;

    private String category;

    private int stock;

    private Boolean status; // Available = true?

    private Boolean underRepair;

    private Boolean deleteStatus;

    private InitialCondition initialCondition;

    private Integer loanFee;

    private Integer penaltyForDelay;

    private Integer replacementValue;

    private Integer damageValue;

    private String description;
}
