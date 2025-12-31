package com.tingeso.tingesoMS_tool.Dtos;

import lombok.Data;

@Data
public class ToolStatusDto {
    private Long idTool;
    private Boolean status;
    private Boolean underRepair;
    private Boolean deleteStatus;
    /*--------------------------*/
    private String email;
}
