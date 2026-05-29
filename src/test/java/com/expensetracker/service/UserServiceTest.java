package com.expensetracker.service;

import com.expensetracker.dto.UserDTO;
import com.expensetracker.dto.UserUpdateDTO;
import com.expensetracker.entity.User;
import com.expensetracker.exception.DuplicateResourceException;
import com.expensetracker.exception.ResourceNotFoundException;
import com.expensetracker.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Unit tests for UserService
 */
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserService userService;

    private User testUser;
    private UserDTO testUserDTO;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setEmail("test@example.com");
        testUser.setFirstName("John");
        testUser.setLastName("Doe");
        testUser.setPassword("hashedPassword");

        testUserDTO = new UserDTO();
        testUserDTO.setId(1L);
        testUserDTO.setEmail("test@example.com");
        testUserDTO.setFirstName("John");
        testUserDTO.setLastName("Doe");
    }

    @Test
    void testRegisterUserSuccess() {
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("hashedPassword");
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        when(modelMapper.map(any(User.class), eq(UserDTO.class))).thenReturn(testUserDTO);

        UserDTO result = userService.registerUser("test@example.com", "John", "Doe", "password123");

        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testRegisterUserDuplicateEmail() {
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        assertThrows(DuplicateResourceException.class,
            () -> userService.registerUser("test@example.com", "John", "Doe", "password123"));
    }

    @Test
    void testGetUserByEmailSuccess() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(testUser));

        User result = userService.getUserByEmail("test@example.com");

        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
    }

    @Test
    void testGetUserByEmailNotFound() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
            () -> userService.getUserByEmail("nonexistent@example.com"));
    }

    @Test
    void testGetUserByIdSuccess() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(testUser));

        User result = userService.getUserById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testGetUserByIdNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
            () -> userService.getUserById(999L));
    }

    @Test
    void testVerifyPasswordSuccess() {
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);

        boolean result = userService.verifyPassword(testUser, "password123");

        assertTrue(result);
    }

    @Test
    void testVerifyPasswordFailure() {
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        boolean result = userService.verifyPassword(testUser, "wrongPassword");

        assertFalse(result);
    }
}
