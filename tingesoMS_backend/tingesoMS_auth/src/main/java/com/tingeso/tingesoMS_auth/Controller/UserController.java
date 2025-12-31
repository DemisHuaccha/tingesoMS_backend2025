package com.tingeso.tingesoMS_auth.Controller;

import com.tingeso.tingesoMS_auth.Entities.User;
import com.tingeso.tingesoMS_auth.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User loginUser) {
        User user = userService.findByEmail(loginUser.getEmail());
        if(user != null && user.getPassword().equals(loginUser.getPassword())) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.status(401).build();
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        return ResponseEntity.ok(userService.register(user));
    }
}
