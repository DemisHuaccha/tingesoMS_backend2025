package com.tingeso.tingesoMS_loan.Dtos;

public class ClientDto {
    private Long idCustomer;
    private String rut;
    private Boolean status;
    private String firstName;
    private String lastName;

    public Long getIdCustomer() { return idCustomer; }
    public void setIdCustomer(Long idCustomer) { this.idCustomer = idCustomer; }

    public String getRut() { return rut; }
    public void setRut(String rut) { this.rut = rut; }

    public Boolean getStatus() { return status; } // Or isStatus? Usually getStatus for Boolean wrapper
    public void setStatus(Boolean status) { this.status = status; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
}
