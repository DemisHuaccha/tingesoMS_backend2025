package com.tingeso.tingesoMS_loan.Dtos;

import lombok.Data;

@Data
public class ToolDto {
    private Long idTool;
    private String name;
    private String category;
    private int stock;
    private Boolean status;
    private Boolean underRepair;
    private Boolean deleteStatus;
}
