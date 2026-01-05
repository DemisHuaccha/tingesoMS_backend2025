package com.tingeso.tingesoMS_inventory.Dtos;

import lombok.Data;

@Data
public class DtoClient {
    private String firstName;
    private String lastName;
    private String rut;
    private Boolean status;
    private String emailC;
    private String phone;

    /*----------------------*/

    private String email;
}
