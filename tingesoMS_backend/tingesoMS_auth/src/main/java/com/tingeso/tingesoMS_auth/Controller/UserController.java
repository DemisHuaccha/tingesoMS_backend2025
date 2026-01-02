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

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user){
        User newUser = userService.register(user);
        return ResponseEntity.ok(newUser);
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
    
    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        User updated = userService.updateUser(id, user);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/getByEmail")
    public ResponseEntity<User> getUserByEmail(@RequestParam String email) {
        User user = userService.findByEmail(email);
        if (user == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(user);
    }

}
