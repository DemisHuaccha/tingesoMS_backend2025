package com.tingeso.tingesoMS_fee.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "global_fees")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GlobalFee {
    @Id
    private String name; // "DELAY_PENALTY", "RENTAL_FEE"
    private Integer value;
}
