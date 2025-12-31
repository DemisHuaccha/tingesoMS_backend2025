package com.tingeso.tingesoMS_auth.Dtos;

import lombok.Data;
import java.time.LocalDate;

@Data
public class CreateUserDto {
    private String name;
    private String email;
    private String password;
    private LocalDate birthday;
    private Boolean admin;
}
