package com.tingeso.tingesoMS_client.Repository;

import com.tingeso.tingesoMS_client.Entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepositorie extends JpaRepository<Client, Long> {
    Optional<Client> findByRut(String rut);
}
