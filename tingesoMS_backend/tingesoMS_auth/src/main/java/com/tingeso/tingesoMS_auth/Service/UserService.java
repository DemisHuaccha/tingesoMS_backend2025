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
    
    public User findById(Long id) {
        return userRepo.findById(id).orElse(null);
    }
    
    public java.util.List<User> findAll() {
        return userRepo.findAll();
    }
    
    public User updateUser(Long id, User userDetails) {
        User user = findById(id);
        if(user != null) {
            user.setName(userDetails.getName());
            user.setEmail(userDetails.getEmail());
            // Add password logic if needed, but keeping simple for now
            return userRepo.save(user);
        }
        return null;
    }
}
