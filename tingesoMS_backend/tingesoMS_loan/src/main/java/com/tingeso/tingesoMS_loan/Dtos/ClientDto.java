package com.tingeso.tingesoMS_loan.Dtos;

import lombok.Data;

@Data
public class ClientDto {
    private Long idCustomer;

    private String name;
    private String email;
    private String rut;
    private String phone;
    private Boolean status; // ACTIVE, RESTRICTED
    private String firstName;
    private String lastName;
}
