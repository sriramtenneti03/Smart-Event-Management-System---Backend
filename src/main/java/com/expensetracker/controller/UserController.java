package com.expensetracker.controller;

import com.expensetracker.dto.ApiResponse;
import com.expensetracker.dto.UserDTO;
import com.expensetracker.dto.UserUpdateDTO;
import com.expensetracker.service.UserService;
import com.expensetracker.util.JwtTokenProvider;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller for user profile endpoints
 */
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    public UserController(UserService userService, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /**
     * Get current user profile
     */
    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<UserDTO>> getCurrentUser(Authentication authentication) {
        logger.info("Fetching profile for user: {}", authentication.getPrincipal());

        UserDTO user = userService.getUserDTOById(((Number) authentication.getCredentials()).longValue());
        return new ResponseEntity<>(
            new ApiResponse<>(true, "User profile retrieved successfully", user),
            HttpStatus.OK
        );
    }

    /**
     * Get user by ID (admin or self)
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDTO>> getUserById(@PathVariable Long id, Authentication authentication) {
        logger.info("Fetching user profile for id: {}", id);

        Long currentUserId = ((Number) authentication.getCredentials()).longValue();
        
        if (!currentUserId.equals(id)) {
            logger.warn("Unauthorized access attempt: {} trying to access {}", currentUserId, id);
            return new ResponseEntity<>(
                new ApiResponse<>(false, "Unauthorized to view this profile", null),
                HttpStatus.FORBIDDEN
            );
        }

        UserDTO user = userService.getUserDTOById(id);
        return new ResponseEntity<>(
            new ApiResponse<>(true, "User profile retrieved successfully", user),
            HttpStatus.OK
        );
    }

    /**
     * Update user profile
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDTO>> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserUpdateDTO updateDTO,
            Authentication authentication) {
        logger.info("Updating user profile for id: {}", id);

        Long currentUserId = ((Number) authentication.getCredentials()).longValue();
        
        if (!currentUserId.equals(id)) {
            logger.warn("Unauthorized access attempt: {} trying to update {}", currentUserId, id);
            return new ResponseEntity<>(
                new ApiResponse<>(false, "Unauthorized to update this profile", null),
                HttpStatus.FORBIDDEN
            );
        }

        UserDTO user = userService.updateUser(id, updateDTO);
        return new ResponseEntity<>(
            new ApiResponse<>(true, "User profile updated successfully", user),
            HttpStatus.OK
        );
    }
}
