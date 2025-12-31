package com.tingeso.tingesoMS_auth.Repository;

import com.tingeso.tingesoMS_auth.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepositorie extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
