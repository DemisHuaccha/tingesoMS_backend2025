package com.tingeso.tingesoMS_client.Dtos;

import lombok.Data;

@Data
public class CreateClientDto {

    private Long idCustomer;
    private String firstName;
    private String lastName;
    private String rut;
    private Boolean status;
    private String emailC;
    private String phone;

    /*----------------------*/

    private String email;


}