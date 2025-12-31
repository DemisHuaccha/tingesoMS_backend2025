package com.tingeso.tingesoMS_auth.Service;

import com.tingeso.tingesoMS_auth.Entities.User;
import com.tingeso.tingesoMS_auth.Repository.UserRepositorie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepositorie userRepo;

    public User register(User user){
        return userRepo.save(user);
    }
    
    public User findByEmail(String email){
        return userRepo.findByEmail(email).orElse(null);
    }
}
