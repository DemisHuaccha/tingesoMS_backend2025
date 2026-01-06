package com.tingeso.tingesoMS_client.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoUser {
    private Long id;

    private String email;
    private String role;
    private String password;
    private String name;

    private String firstName;
    private String lastName;
    private String phone;
}
