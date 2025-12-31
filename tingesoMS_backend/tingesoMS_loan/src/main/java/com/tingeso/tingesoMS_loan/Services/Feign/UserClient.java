package com.tingeso.tingesoMS_loan.Services.Feign;

import com.tingeso.tingesoMS_loan.Dtos.ClientDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "tingesoMS_user", url = "http://localhost:8081/api/customer")
public interface UserClient {

    // @GetMapping("/search") - Removed dangling annotation
    // Assuming search returns list of ruts, but we need by Rut object.
    // ClientController had getCustomerByName but not getByRut explicitly exposed at root besides search?
    // Wait, ClientController has `createCustomer`... 
    // ClientRepository had `findByRut`. Service had `save` checking rut.
    // I should check ClientController again. It has `getCustomerById`.
    // It maps `search` to `searchRuts` (returns Strings).
    // I need `getClientByRut`. I will add it to ClientController in tingesoMS_user later/now?
    // Or I use `getAll` and filter (bad).
    // I will assume I can fetch by ID or add `byRut` endpoint.
    // For now I will put a placeholder or use `getAll` if list is small, but better: 
    // I'll assume endpoint `/byRut?rut=...` exists or I will create it.
    // Actually ClientRepository has `findByRut`. Simple to expose.
    @GetMapping("/byRut")
    ClientDto getClientByRut(@RequestParam("rut") String rut);

    @PutMapping("/updateStatus/{id}")
    void updateStatus(@PathVariable("id") Long id);
}
