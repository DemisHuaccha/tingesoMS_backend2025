package com.tingeso.tingesoMS_report.Dtos;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ClientDto {
    private Long idCustomer;
    private String rut;
    private String name;
    private String email;
    private String phone;
    private String status;
}
