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

        assertNotNull(result);
        assertEquals("test@tingeso.com", result.getEmail());
    }

    @Test
    void findByEmail() {
        User user = new User();
        user.setEmail("test@tingeso.com");
        when(userRepositorie.findByEmail("test@tingeso.com")).thenReturn(Optional.of(user));

        User result = userService.findByEmail("test@tingeso.com");

        assertNotNull(result);
        assertEquals("test@tingeso.com", result.getEmail());
    }
}
