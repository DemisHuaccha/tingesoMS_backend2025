package com.tingeso.tingesoMS_kardex.Dtos;

import lombok.Data;

@Data
public class DtoUser {
    private String name;
    private String email;
    private String rut;
    private String phone;
    private Boolean status; // ACTIVE, RESTRICTED

    // Strict Monolith Fields
    private String firstName;
    private String lastName;
}
