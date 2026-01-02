package com.tingeso.tingesoMS_auth.Service;

import com.tingeso.tingesoMS_auth.Entities.User;
import com.tingeso.tingesoMS_auth.Repository.UserRepositorie;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepositorie userRepositorie;

    @InjectMocks
    private UserService userService;

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
        User user = new User();
        user.setId(1L);
        user.setName("Old");
        
        User updates = new User();
        updates.setName("New");
        
        when(userRepositorie.findById(1L)).thenReturn(Optional.of(user));
        when(userRepositorie.save(any(User.class))).thenReturn(user);
        
        User result = userService.updateUser(1L, updates);
        assertNotNull(result);
        assertEquals("New", result.getName());
    }
    
    @Test
    void updateUser_NotFound() {
       User updates = new User();
       updates.setName("New");
       when(userRepositorie.findById(99L)).thenReturn(Optional.empty());
       
       User result = userService.updateUser(99L, updates);
       assertNull(result);
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
