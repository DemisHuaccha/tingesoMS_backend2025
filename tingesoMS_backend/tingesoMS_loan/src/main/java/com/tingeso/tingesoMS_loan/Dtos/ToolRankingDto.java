package com.tingeso.tingesoMS_loan.Dtos;

public class ToolRankingDto {

    private String nameTool;
    private String categoryTool;
    private Integer feeTool;
    private Long quantityTool;

    public ToolRankingDto(String nameTool, String categoryTool, Integer feeTool, Long quantityTool) {
        this.nameTool = nameTool;
        this.categoryTool = categoryTool;
        this.feeTool = feeTool;
        this.quantityTool = quantityTool;
    }

    public String getNameTool() { return nameTool; }
    public void setNameTool(String nameTool) { this.nameTool = nameTool; }

    public String getCategoryTool() { return categoryTool; }
    public void setCategoryTool(String categoryTool) { this.categoryTool = categoryTool; }

    public Integer getFeeTool() { return feeTool; }
    public void setFeeTool(Integer feeTool) { this.feeTool = feeTool; }

    public Long getQuantityTool() { return quantityTool; }
    public void setQuantityTool(Long quantityTool) { this.quantityTool = quantityTool; }
}
