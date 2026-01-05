package com.tingeso.tingesoMS_auth.Controller;

import com.tingeso.tingesoMS_auth.Dtos.CreateUserDto;
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

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user){
        User newUser = userService.register(user);
        return ResponseEntity.ok(newUser);
    }

    @PostMapping("/createUser")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User existing = userService.findByEmail(user.getEmail());
        if (existing != null) {
            return ResponseEntity.ok(existing);
        }
        return ResponseEntity.ok(userService.register(user));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id) {
        User user = userService.findById(id);
        if (user == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(user);
    }
    
    @GetMapping("/all")
    public ResponseEntity<java.util.List<User>> getAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @PutMapping("/updateUser")
    public ResponseEntity<CreateUserDto> updateUser(@RequestBody CreateUserDto user) {
        userService.updateUser(user);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/getByEmail")
    public ResponseEntity<User> getUserByEmail(@RequestParam String email) {
        User user = userService.findByEmail(email);
        if (user == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(user);
    }

}
