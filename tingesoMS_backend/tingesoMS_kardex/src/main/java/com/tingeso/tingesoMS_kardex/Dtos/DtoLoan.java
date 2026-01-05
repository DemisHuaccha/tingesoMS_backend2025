package com.tingeso.tingesoMS_kardex.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoLoan {
    private Long loanId;
    private LocalDate deliveryDate;
    private LocalDate returnDate;
    private Boolean loanStatus;
    private Boolean penalty;
    private Integer penaltyTotal;
    private Integer priceToPay;

    private Long idClient;
    private String clientRut;
    private String email;

    private Long idTool;
    private String name;
    private String category;
    private Integer stock;
    private Boolean status;
    private Boolean underRepair;
    private Boolean deleteStatus;
    private String description;
    private Integer quantity;

    private InitialCondition initialCondition;
    private Integer loanFee;
    private Integer penaltyForDelay;
    private Integer replacementValue;
    private Integer damageValue;

}
