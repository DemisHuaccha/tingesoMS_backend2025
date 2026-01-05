package com.tingeso.tingesoMS_auth.Dtos;

import lombok.Data;
import java.time.LocalDate;

@Data
public class CreateUserDto {
    private String email;
    private String role;
    private String password;
    private String name;

    private String firstName;
    private String lastName;
    private String phone;
}
