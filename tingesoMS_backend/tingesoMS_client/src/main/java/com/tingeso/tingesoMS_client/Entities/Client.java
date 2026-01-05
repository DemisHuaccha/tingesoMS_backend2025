package com.tingeso.tingesoMS_client.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "client")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCustomer;

    private String name;
    private String email;
    private String rut;
    private String phone;
    private Boolean status; // ACTIVE, RESTRICTED
    
    // Strict Monolith Fields
    private String firstName;
    private String lastName;
}
