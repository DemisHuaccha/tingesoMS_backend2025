package com.tingeso.tingesoMS_kardex.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "cardex")
public class Cardex {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("moveDate")
    private LocalDate moveDate;

    @JsonProperty("typeMove")
    private String typeMove;

    @Column(name = "description")
    private String description;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "id_Tool", nullable = true)
    private Long toolId;

    @Column(name = "id_Loan", nullable = true)
    private Long loanId;

    @Column(name = "id_client", nullable = true)
    private Long clientId;
    
    @Column(name = "client_rut", nullable = true)
    private String clientRut;
}
