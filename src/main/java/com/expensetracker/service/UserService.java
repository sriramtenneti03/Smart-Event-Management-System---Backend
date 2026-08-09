package com.expensetracker.service;

import com.expensetracker.dto.UserDTO;
import com.expensetracker.dto.UserUpdateDTO;
import com.expensetracker.entity.User;
import com.expensetracker.exception.DuplicateResourceException;
import com.expensetracker.exception.ResourceNotFoundException;
import com.expensetracker.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Service class for User entity operations
 */
@Service
@Transactional
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    /**
     * Register a new user
     */
    public UserDTO registerUser(String email, String firstName, String lastName, String password) {
        if (userRepository.existsByEmail(email)) {
            logger.warn("Attempt to register with existing email: {}", email);
            throw new DuplicateResourceException("Email is already registered");
        }

        User user = new User();
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(passwordEncoder.encode(password));

        user = userRepository.save(user);
        logger.info("User registered successfully with email: {}", email);
        return modelMapper.map(user, UserDTO.class);
    }

    /**
     * Get user by email
     */
    @Transactional(readOnly = true)
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
    }

    /**
     * Get user by ID
     */
    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    /**
     * Get user DTO by ID
     */
    @Transactional(readOnly = true)
    public UserDTO getUserDTOById(Long id) {
        User user = getUserById(id);
        return modelMapper.map(user, UserDTO.class);
    }

    /**
     * Update user information
     */
    public UserDTO updateUser(Long id, UserUpdateDTO updateDTO) {
        User user = getUserById(id);
        user.setFirstName(updateDTO.getFirstName());
        user.setLastName(updateDTO.getLastName());
        user = userRepository.save(user);
        logger.info("User updated successfully with id: {}", id);
        return modelMapper.map(user, UserDTO.class);
    }

    /**
     * Verify user password
     */
    @Transactional(readOnly = true)
    public boolean verifyPassword(User user, String rawPassword) {
        return passwordEncoder.matches(rawPassword, user.getPassword());
    }
}
