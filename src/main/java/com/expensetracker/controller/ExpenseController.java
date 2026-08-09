package com.expensetracker.controller;

import com.expensetracker.dto.*;
import com.expensetracker.entity.User;
import com.expensetracker.service.ExpenseService;
import com.expensetracker.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

/**
 * Controller for expense endpoints
 */
@RestController
@RequestMapping("/api/expenses")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ExpenseController {

    private static final Logger logger = LoggerFactory.getLogger(ExpenseController.class);

    private final ExpenseService expenseService;
    private final UserService userService;

    public ExpenseController(ExpenseService expenseService, UserService userService) {
        this.expenseService = expenseService;
        this.userService = userService;
    }

    /**
     * Create a new expense
     */
    @PostMapping
    public ResponseEntity<ApiResponse<ExpenseDTO>> createExpense(
            @Valid @RequestBody ExpenseDTO expenseDTO,
            Authentication authentication) {
        logger.info("Creating new expense");

        Long userId = ((Number) authentication.getCredentials()).longValue();
        User user = userService.getUserById(userId);

        ExpenseDTO createdExpense = expenseService.createExpense(user, expenseDTO);
        return new ResponseEntity<>(
            new ApiResponse<>(true, "Expense created successfully", createdExpense),
            HttpStatus.CREATED
        );
    }

    /**
     * Get all expenses with pagination
     */
    @GetMapping
    public ResponseEntity<ApiResponse<Page<ExpenseDTO>>> getUserExpenses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int pageSize,
            Authentication authentication) {
        logger.info("Fetching user expenses - page: {}, pageSize: {}", page, pageSize);

        Long userId = ((Number) authentication.getCredentials()).longValue();
        User user = userService.getUserById(userId);

        Page<ExpenseDTO> expenses = expenseService.getUserExpenses(user, page, pageSize);
        return new ResponseEntity<>(
            new ApiResponse<>(true, "Expenses retrieved successfully", expenses),
            HttpStatus.OK
        );
    }

    /**
     * Get expenses with filters
     */
    @PostMapping("/filter")
    public ResponseEntity<ApiResponse<Page<ExpenseDTO>>> getExpensesWithFilters(
            @Valid @RequestBody ExpenseFilterDTO filterDTO,
            Authentication authentication) {
        logger.info("Fetching expenses with filters");

        Long userId = ((Number) authentication.getCredentials()).longValue();
        User user = userService.getUserById(userId);

        Page<ExpenseDTO> expenses = expenseService.getExpensesWithFilters(user, filterDTO);
        return new ResponseEntity<>(
            new ApiResponse<>(true, "Filtered expenses retrieved successfully", expenses),
            HttpStatus.OK
        );
    }

    /**
     * Get expense by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ExpenseDTO>> getExpenseById(
            @PathVariable Long id,
            Authentication authentication) {
        logger.info("Fetching expense with id: {}", id);

        Long userId = ((Number) authentication.getCredentials()).longValue();
        User user = userService.getUserById(userId);

        ExpenseDTO expense = expenseService.getExpenseDTOById(id, user);
        return new ResponseEntity<>(
            new ApiResponse<>(true, "Expense retrieved successfully", expense),
            HttpStatus.OK
        );
    }

    /**
     * Update expense
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ExpenseDTO>> updateExpense(
            @PathVariable Long id,
            @Valid @RequestBody ExpenseDTO expenseDTO,
            Authentication authentication) {
        logger.info("Updating expense with id: {}", id);

        Long userId = ((Number) authentication.getCredentials()).longValue();
        User user = userService.getUserById(userId);

        ExpenseDTO updatedExpense = expenseService.updateExpense(id, user, expenseDTO);
        return new ResponseEntity<>(
            new ApiResponse<>(true, "Expense updated successfully", updatedExpense),
            HttpStatus.OK
        );
    }

    /**
     * Delete expense
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteExpense(
            @PathVariable Long id,
            Authentication authentication) {
        logger.info("Deleting expense with id: {}", id);

        Long userId = ((Number) authentication.getCredentials()).longValue();
        User user = userService.getUserById(userId);

        expenseService.deleteExpense(id, user);
        return new ResponseEntity<>(
            new ApiResponse<>(true, "Expense deleted successfully", null),
            HttpStatus.OK
        );
    }

    /**
     * Get monthly spending summary
     */
    @GetMapping("/summary/monthly/{yearMonth}")
    public ResponseEntity<ApiResponse<ExpenseSummaryDTO>> getMonthlySummary(
            @PathVariable String yearMonth,
            Authentication authentication) {
        logger.info("Fetching monthly summary for: {}", yearMonth);

        Long userId = ((Number) authentication.getCredentials()).longValue();
        User user = userService.getUserById(userId);

        YearMonth month = YearMonth.parse(yearMonth);
        ExpenseSummaryDTO summary = expenseService.getMonthlySummary(user, month);
        return new ResponseEntity<>(
            new ApiResponse<>(true, "Monthly summary retrieved successfully", summary),
            HttpStatus.OK
        );
    }

    /**
     * Get category-wise expense breakdown
     */
    @GetMapping("/analytics/category-breakdown")
    public ResponseEntity<ApiResponse<List<CategoryExpenseDTO>>> getCategoryBreakdown(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            Authentication authentication) {
        logger.info("Fetching category-wise expense breakdown");

        Long userId = ((Number) authentication.getCredentials()).longValue();
        User user = userService.getUserById(userId);

        LocalDate start = startDate != null ? startDate : LocalDate.now().minusMonths(1);
        LocalDate end = endDate != null ? endDate : LocalDate.now();

        List<CategoryExpenseDTO> breakdown = expenseService.getCategoryWiseExpenses(user, start, end);
        return new ResponseEntity<>(
            new ApiResponse<>(true, "Category breakdown retrieved successfully", breakdown),
            HttpStatus.OK
        );
    }

    /**
     * Get spending trends for multiple months
     */
    @GetMapping("/analytics/trends")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getSpendingTrends(
            @RequestParam(defaultValue = "6") int months,
            Authentication authentication) {
        logger.info("Fetching spending trends for {} months", months);

        Long userId = ((Number) authentication.getCredentials()).longValue();
        User user = userService.getUserById(userId);

        Map<String, Object> trends = (Map<String, Object>) (Object) expenseService.getSpendingTrends(user, months);
        return new ResponseEntity<>(
            new ApiResponse<>(true, "Spending trends retrieved successfully", trends),
            HttpStatus.OK
        );
    }
}
