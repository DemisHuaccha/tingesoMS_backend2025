package com.tingeso.tingesoMS_auth.Service;

import com.tingeso.tingesoMS_auth.Dtos.CreateUserDto;
import com.tingeso.tingesoMS_auth.Entities.User;
import com.tingeso.tingesoMS_auth.Repository.UserRepositorie;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepositorie userRepositorie;

    // --- Helper para crear datos dentro de los tests ---
    private User createAndSaveUser(String email, String role, String name) {
        User user = new User();
        user.setEmail(email);
        user.setRole(role);
        user.setFirstName(name);
        user.setLastName("TestLast");
        user.setPhone("+56911111111");
        return userRepositorie.save(user);
    }

    @Test
    void register() {
        User user = new User();
        user.setEmail("test@tingeso.com");
        user.setName("Test User");
        
        when(userRepositorie.save(any(User.class))).thenReturn(user);

        User result = userService.register(user);

        assertEquals("test@tingeso.com", result.getEmail());
    }
    
    @Test
    void findById() {
        User user = new User();
        user.setId(1L);
        when(userRepositorie.findById(1L)).thenReturn(Optional.of(user));
        
        User result = userService.findById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }
    
    @Test
    void findAll() {
        when(userRepositorie.findAll()).thenReturn(java.util.List.of(new User()));
        assertFalse(userService.findAll().isEmpty());
    }
    
    @Test
    void updateUser() {
        // 1. Crear usuario original
        createAndSaveUser("update@example.com", "USER", "OriginalName");

        // 2. DTO con datos nuevos (mismo email)
        CreateUserDto updatedDto = new CreateUserDto();
        updatedDto.setEmail("update@example.com");
        updatedDto.setRole("ADMIN"); // Cambio de rol
        updatedDto.setFirstName("UpdatedName"); // Cambio de nombre
        updatedDto.setLastName("UpdatedLast");
        updatedDto.setPhone("+56999999999");

        userService.updateUser(updatedDto);

        // 3. Verificar cambios
        User updated = userRepositorie.findByEmail("update@example.com").orElseThrow();
        assertEquals("UpdatedName", updated.getFirstName());
        assertEquals("ADMIN", updated.getRole());
    }

    @Test
    void findByEmail() {
        User user = new User();
        user.setEmail("test@email.com");
        when(userRepositorie.findByEmail("test@email.com")).thenReturn(Optional.of(user));
        
        User result = userService.findByEmail("test@email.com");
        assertNotNull(result);
        assertEquals("test@email.com", result.getEmail());
    }
    
    @Test
    void save_ExistingUser() {
        User user = new User();
        user.setEmail("exist@email.com");
        
        when(userRepositorie.save(any(User.class))).thenReturn(user);
        
        User result = userService.register(user);
        assertNotNull(result);
        assertEquals("exist@email.com", result.getEmail());
    }
}
