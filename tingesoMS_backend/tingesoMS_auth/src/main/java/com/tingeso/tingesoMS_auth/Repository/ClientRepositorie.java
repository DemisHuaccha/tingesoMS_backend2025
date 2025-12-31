package com.tingeso.tingesoMS_user.Repository;

import com.tingeso.tingesoMS_user.Entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepositorie extends JpaRepository<Client,Long> {
    Optional<Client> findCustomerByFirstName(String firstName);

    Optional<Client> findByRut(String rut);

    List<Client> findByRutContainingIgnoreCase(String rut);

}
