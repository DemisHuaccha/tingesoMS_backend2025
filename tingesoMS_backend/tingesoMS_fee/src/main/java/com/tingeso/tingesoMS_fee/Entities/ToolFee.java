package com.tingeso.tingesoMS_fee.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tool_fees")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ToolFee {

    @Id
    private Long toolId; // Same ID as Inventory

    private Integer loanFee;
    private Integer penaltyForDelay; // Can be overridden per tool or global?
    private Integer damageValue;
    private Integer replacementValue;
}
