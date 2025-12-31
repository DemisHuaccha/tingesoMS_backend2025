package com.tingeso.tingesoMS_inventory.Dtos;

import lombok.Data;

@Data
public class CreateToolDto {
    private String name;
    private String category;
    private int stock;
}
