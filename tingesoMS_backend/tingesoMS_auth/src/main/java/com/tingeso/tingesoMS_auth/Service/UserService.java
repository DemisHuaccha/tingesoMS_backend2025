package com.tingeso.tingesoMS_auth.Service;

import com.tingeso.tingesoMS_auth.Dtos.CreateUserDto;
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

    public void updateUser(CreateUserDto user){
        Optional<User> userP=userRepo.findByEmail(user.getEmail());
        if(userP.isPresent()){
            User oldUser=userP.get();
            oldUser.setEmail(user.getEmail());
            oldUser.setRole(user.getRole());
            oldUser.setFirstName(user.getFirstName());
            oldUser.setLastName(user.getLastName());
            oldUser.setPhone(user.getPhone());
            userRepo.save(oldUser);
        }else {
            throw new IllegalArgumentException("User with email " + user.getEmail() + " not found");
        }
    }
}
