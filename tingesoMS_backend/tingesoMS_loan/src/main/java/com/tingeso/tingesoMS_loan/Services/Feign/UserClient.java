package com.tingeso.tingesoMS_loan.Services.Feign;

import com.tingeso.tingesoMS_loan.Dtos.ClientDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "tingesoMS_user", url = "http://localhost:8081/api/customer")
public interface UserClient {


    @GetMapping("/byRut")
    ClientDto getClientByRut(@RequestParam("rut") String rut);

    @PutMapping("/updateStatus/{id}")
    void updateStatus(@PathVariable("id") Long id);
}
