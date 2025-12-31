package com.tingeso.tingesoMS_loan.Dtos;

import lombok.Data;

@Data
public class ReturnLoanDto {

    private Long loanId;
    private String email;

    public Long getLoanId() {
        return loanId;
    }
    public void setLoanId(Long loanId) {
        this.loanId = loanId;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
