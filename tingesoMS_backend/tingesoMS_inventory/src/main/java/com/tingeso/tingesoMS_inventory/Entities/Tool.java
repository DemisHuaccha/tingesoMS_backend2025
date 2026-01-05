package com.tingeso.tingesoMS_inventory.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tools")
public class Tool {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long idTool;

    @JsonProperty("name")
    private String name;

    @JsonProperty("category")
    private String category;

    @JsonProperty("stock")
    private int stock;

    @JsonProperty("status")
    private Boolean status; // Available = true?

    @JsonProperty("underRepair")
    private Boolean underRepair;

    @JsonProperty("deleteStatus")
    private Boolean deleteStatus;

    @Enumerated(EnumType.STRING)
    @JsonProperty("initialCondition")
    private com.tingeso.tingesoMS_inventory.Dtos.InitialCondition initialCondition;

    @JsonProperty("loanFee")
    private Integer loanFee;
    
    @JsonProperty("penaltyForDelay")
    private Integer penaltyForDelay;
    
    @JsonProperty("replacementValue")
    private Integer replacementValue;
    
    @JsonProperty("damageValue")
    private Integer damageValue;
    
    @JsonProperty("description")
    private String description;
}
