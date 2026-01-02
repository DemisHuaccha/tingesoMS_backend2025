package com.tingeso.tingesoMS_inventory.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupToolsDto {
    private String category;
    private String name;
    private Long count; // Added count as it makes sense for grouping
}
