package com.tingeso.tingesoMS_kardex.Dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class CreateToolDto {
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

    private Integer quantity;

    private String email;

}
