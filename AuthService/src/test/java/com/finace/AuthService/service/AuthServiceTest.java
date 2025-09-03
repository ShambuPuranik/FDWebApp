package com.finace.AuthService.service;

import com.finace.AuthService.dto.UserDTO;
import com.finace.AuthService.dto.AuthResponse;
import com.finace.AuthService.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthService authService;

    @Test
    void testRegister() throws Exception {
        UserDTO user = new UserDTO(null, "testuser", "password", "USER");
        when(userRepository.save(any())).thenReturn(user);

        AuthResponse response = authService.register(user);

        assertNotNull(response);
        assertTrue(response.getToken() != null);
    }
}
