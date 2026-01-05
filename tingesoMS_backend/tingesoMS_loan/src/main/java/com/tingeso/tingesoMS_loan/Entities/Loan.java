package com.tingeso.tingesoMS_loan.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "loan")
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLoan; 

    @Column(name = "deliveryDate")
    private LocalDate deliveryDate;

    @Column(name = "returnDate")
    private LocalDate returnDate;

    // References to other services
    @Column(name = "id_Customer")
    private Long clientId;

    @Column(name = "id_Tool")
    private Long toolId;

    // Snapshots for Reporting
    @Column(name = "client_rut")
    private String clientRut;

    @Column(name = "tool_name")
    private String toolName;
    
    @Column(name = "tool_category")
    private String toolCategory;
    
    @Column(name = "tool_loan_fee")
    private Integer toolLoanFee;

    @Column(name = "penalty_field") // Using distinct name to avoid keyword issues if any
    private Integer penaltyForDelay;

    //True es que esta activa
    //False es que ya fue completada(Es decir no se tiene en cuenta)
    @Column(name = "loan_status")
    private Boolean loanStatus;

    //Booleano que indica si hay atraso
    @Column(name = "penalty")
    private Boolean penalty;

    @Column(name = "penalty_total")
    private Integer penaltyTotal;

    @Column(name = "price_to_pay")
    private Integer priceToPay;
}
