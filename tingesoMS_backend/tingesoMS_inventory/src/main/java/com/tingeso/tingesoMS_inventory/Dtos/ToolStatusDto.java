package com.tingeso.tingesoMS_inventory.Dtos;

import lombok.Data;

@Data
public class ToolStatusDto {
    private Long idTool;
    private Boolean status;
    private Boolean underRepair;
    private Boolean deleteStatus;
}
