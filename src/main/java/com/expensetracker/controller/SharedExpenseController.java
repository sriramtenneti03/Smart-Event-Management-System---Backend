package com.expensetracker.controller;

import com.expensetracker.dto.ApiResponse;
import com.expensetracker.dto.SharedExpenseDTO;
import com.expensetracker.dto.SharedExpenseCreateDTO;
import com.expensetracker.entity.User;
import com.expensetracker.service.SharedExpenseService;
import com.expensetracker.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

/**
 * Controller for shared expense and bill splitting endpoints
 */
@RestController
@RequestMapping("/api/shared-expenses")
@CrossOrigin(origins = "*", maxAge = 3600)
public class SharedExpenseController {

    private static final Logger logger = LoggerFactory.getLogger(SharedExpenseController.class);

    private final SharedExpenseService sharedExpenseService;
    private final UserService userService;

    public SharedExpenseController(SharedExpenseService sharedExpenseService, UserService userService) {
        this.sharedExpenseService = sharedExpenseService;
        this.userService = userService;
    }

    /**
     * Create a shared expense with bill splits
     */
    @PostMapping
    public ResponseEntity<ApiResponse<SharedExpenseDTO>> createSharedExpense(
            @Valid @RequestBody SharedExpenseCreateDTO createDTO,
            Authentication authentication) {
        logger.info("Creating new shared expense");

        Long userId = ((Number) authentication.getCredentials()).longValue();
        User user = userService.getUserById(userId);

        SharedExpenseDTO createdExpense = sharedExpenseService.createSharedExpense(user, createDTO);
        return new ResponseEntity<>(
            new ApiResponse<>(true, "Shared expense created successfully", createdExpense),
            HttpStatus.CREATED
        );
    }

    /**
     * Get all shared expenses for user
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<SharedExpenseDTO>>> getUserSharedExpenses(
            Authentication authentication) {
        logger.info("Fetching user's shared expenses");

        Long userId = ((Number) authentication.getCredentials()).longValue();
        User user = userService.getUserById(userId);

        List<SharedExpenseDTO> expenses = sharedExpenseService.getUserSharedExpenses(user);
        return new ResponseEntity<>(
            new ApiResponse<>(true, "Shared expenses retrieved successfully", expenses),
            HttpStatus.OK
        );
    }

    /**
     * Get shared expense by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SharedExpenseDTO>> getSharedExpenseById(
            @PathVariable Long id,
            Authentication authentication) {
        logger.info("Fetching shared expense with id: {}", id);

        Long userId = ((Number) authentication.getCredentials()).longValue();
        User user = userService.getUserById(userId);

        SharedExpenseDTO expense = sharedExpenseService.getSharedExpenseDTOById(id, user);
        return new ResponseEntity<>(
            new ApiResponse<>(true, "Shared expense retrieved successfully", expense),
            HttpStatus.OK
        );
    }

    /**
     * Get unsettled shared expenses
     */
    @GetMapping("/unsettled/list")
    public ResponseEntity<ApiResponse<List<SharedExpenseDTO>>> getUnsettledExpenses(
            Authentication authentication) {
        logger.info("Fetching unsettled shared expenses");

        Long userId = ((Number) authentication.getCredentials()).longValue();
        User user = userService.getUserById(userId);

        List<SharedExpenseDTO> expenses = sharedExpenseService.getUnsettledExpenses(user);
        return new ResponseEntity<>(
            new ApiResponse<>(true, "Unsettled shared expenses retrieved successfully", expenses),
            HttpStatus.OK
        );
    }

    /**
     * Mark shared expense as settled
     */
    @PutMapping("/{id}/settle")
    public ResponseEntity<ApiResponse<SharedExpenseDTO>> settleSharedExpense(
            @PathVariable Long id,
            Authentication authentication) {
        logger.info("Settling shared expense with id: {}", id);

        Long userId = ((Number) authentication.getCredentials()).longValue();
        User user = userService.getUserById(userId);

        SharedExpenseDTO expense = sharedExpenseService.settleSharedExpense(id, user);
        return new ResponseEntity<>(
            new ApiResponse<>(true, "Shared expense settled successfully", expense),
            HttpStatus.OK
        );
    }

    /**
     * Delete shared expense
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteSharedExpense(
            @PathVariable Long id,
            Authentication authentication) {
        logger.info("Deleting shared expense with id: {}", id);

        Long userId = ((Number) authentication.getCredentials()).longValue();
        User user = userService.getUserById(userId);

        sharedExpenseService.deleteSharedExpense(id, user);
        return new ResponseEntity<>(
            new ApiResponse<>(true, "Shared expense deleted successfully", null),
            HttpStatus.OK
        );
    }
}
