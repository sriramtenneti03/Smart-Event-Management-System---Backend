package com.expensetracker.controller;

import com.expensetracker.dto.ApiResponse;
import com.expensetracker.dto.CategoryDTO;
import com.expensetracker.dto.CategoryCreateDTO;
import com.expensetracker.entity.User;
import com.expensetracker.service.CategoryService;
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
 * Controller for category endpoints
 */
@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CategoryController {

    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    private final CategoryService categoryService;
    private final UserService userService;

    public CategoryController(CategoryService categoryService, UserService userService) {
        this.categoryService = categoryService;
        this.userService = userService;
    }

    /**
     * Get all categories for user
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryDTO>>> getUserCategories(Authentication authentication) {
        logger.info("Fetching categories for user");

        Long userId = ((Number) authentication.getCredentials()).longValue();
        User user = userService.getUserById(userId);

        List<CategoryDTO> categories = categoryService.getUserCategories(user);
        return new ResponseEntity<>(
            new ApiResponse<>(true, "Categories retrieved successfully", categories),
            HttpStatus.OK
        );
    }

    /**
     * Create a new category
     */
    @PostMapping
    public ResponseEntity<ApiResponse<CategoryDTO>> createCategory(
            @Valid @RequestBody CategoryCreateDTO createDTO,
            Authentication authentication) {
        logger.info("Creating new category: {}", createDTO.getName());

        Long userId = ((Number) authentication.getCredentials()).longValue();
        User user = userService.getUserById(userId);

        CategoryDTO category = categoryService.createCategory(user, createDTO);
        return new ResponseEntity<>(
            new ApiResponse<>(true, "Category created successfully", category),
            HttpStatus.CREATED
        );
    }

    /**
     * Get category by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryDTO>> getCategoryById(
            @PathVariable Long id,
            Authentication authentication) {
        logger.info("Fetching category with id: {}", id);

        Long userId = ((Number) authentication.getCredentials()).longValue();
        User user = userService.getUserById(userId);

        CategoryDTO category = categoryService.getCategoryDTOById(id, user);
        return new ResponseEntity<>(
            new ApiResponse<>(true, "Category retrieved successfully", category),
            HttpStatus.OK
        );
    }

    /**
     * Update category
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryDTO>> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryCreateDTO updateDTO,
            Authentication authentication) {
        logger.info("Updating category with id: {}", id);

        Long userId = ((Number) authentication.getCredentials()).longValue();
        User user = userService.getUserById(userId);

        CategoryDTO category = categoryService.updateCategory(id, user, updateDTO);
        return new ResponseEntity<>(
            new ApiResponse<>(true, "Category updated successfully", category),
            HttpStatus.OK
        );
    }

    /**
     * Delete category
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(
            @PathVariable Long id,
            Authentication authentication) {
        logger.info("Deleting category with id: {}", id);

        Long userId = ((Number) authentication.getCredentials()).longValue();
        User user = userService.getUserById(userId);

        categoryService.deleteCategory(id, user);
        return new ResponseEntity<>(
            new ApiResponse<>(true, "Category deleted successfully", null),
            HttpStatus.OK
        );
    }
}
