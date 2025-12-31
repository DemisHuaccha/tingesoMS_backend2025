package com.tingeso.tingesoMS_loan.Dtos;

public class ToolStatusDto {
    private Long idTool;
    private Boolean status;
    private Boolean underRepair;
    private Boolean deleteStatus;
    private String email;

    public Long getIdTool() { return idTool; }
    public void setIdTool(Long idTool) { this.idTool = idTool; }

    public Boolean getStatus() { return status; }
    public void setStatus(Boolean status) { this.status = status; }

    public Boolean getUnderRepair() { return underRepair; }
    public void setUnderRepair(Boolean underRepair) { this.underRepair = underRepair; }

    public Boolean getDeleteStatus() { return deleteStatus; }
    public void setDeleteStatus(Boolean deleteStatus) { this.deleteStatus = deleteStatus; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
