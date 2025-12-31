package com.tingeso.tingesoMS_user.Dtos;

import lombok.Data;

@Data
public class CreateUserDto {

    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private String phone;
}
