package com.tingeso.tingesoMS_loan.Dtos;

import lombok.Data;

@Data
public class ClientDto {
    private Long idCustomer;
    private String rut;
    private String name;
    private String email;
    private String phone;
    private String status;
}
