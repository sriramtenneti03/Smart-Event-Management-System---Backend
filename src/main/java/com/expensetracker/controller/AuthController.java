package com.expensetracker.controller;

import com.expensetracker.dto.*;
import com.expensetracker.entity.User;
import com.expensetracker.service.UserService;
import com.expensetracker.util.JwtTokenProvider;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller for authentication endpoints
 */
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(UserService userService, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /**
     * Register a new user
     */
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @RequestBody RegistrationRequest request) {
        logger.info("New registration request for email: {}", request.getEmail());

        UserDTO userDTO = userService.registerUser(
            request.getEmail(),
            request.getFirstName(),
            request.getLastName(),
            request.getPassword()
        );

        String token = jwtTokenProvider.generateToken(userDTO.getEmail(), userDTO.getId());

        AuthResponse response = new AuthResponse();
        response.setToken(token);
        response.setType("Bearer");
        response.setUser(userDTO);

        return new ResponseEntity<>(
            new ApiResponse<>(true, "User registered successfully", response),
            HttpStatus.CREATED
        );
    }

    /**
     * Login user
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody AuthRequest request) {
        logger.info("Login request for email: {}", request.getEmail());

        User user = userService.getUserByEmail(request.getEmail());

        if (!userService.verifyPassword(user, request.getPassword())) {
            logger.warn("Invalid password for user: {}", request.getEmail());
            return new ResponseEntity<>(
                new ApiResponse<>(false, "Invalid credentials", null),
                HttpStatus.UNAUTHORIZED
            );
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setCreatedAt(user.getCreatedAt());
        userDTO.setUpdatedAt(user.getUpdatedAt());

        String token = jwtTokenProvider.generateToken(user.getEmail(), user.getId());

        AuthResponse response = new AuthResponse();
        response.setToken(token);
        response.setType("Bearer");
        response.setUser(userDTO);

        logger.info("User logged in successfully: {}", request.getEmail());
        return new ResponseEntity<>(
            new ApiResponse<>(true, "User logged in successfully", response),
            HttpStatus.OK
        );
    }

    /**
     * Health check endpoint
     */
    @GetMapping("/health")
    public ResponseEntity<ApiResponse<String>> health() {
        return new ResponseEntity<>(
            new ApiResponse<>(true, "Service is healthy", "OK"),
            HttpStatus.OK
        );
    }
}
