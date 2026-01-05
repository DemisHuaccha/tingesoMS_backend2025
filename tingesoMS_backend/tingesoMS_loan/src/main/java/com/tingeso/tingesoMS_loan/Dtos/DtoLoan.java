package com.tingeso.tingesoMS_loan.Dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DtoLoan {

    private Long loanId;

    private LocalDate deliveryDate;

    private LocalDate returnDate;

    private Long idclient;

    private String clientRut;

    private Long idtool;

    private Boolean loanStatus;

    private Boolean penalty;

    private Integer penaltyTotal;

    private Integer priceToPay;

    private Long idTool;

    private String name;

    private String category;

    private int stock;

    private Boolean status; // Available = true?

    private Boolean underRepair;

    private Boolean deleteStatus;

    private InitialCondition initialCondition;

    private Integer loanFee;

    private Integer penaltyForDelay;

    private Integer replacementValue;

    private Integer damageValue;

    private String description;

    private Integer quantity;

    private String email;

}
