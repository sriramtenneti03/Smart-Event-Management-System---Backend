package com.expensetracker.service;

import com.expensetracker.dto.CategoryDTO;
import com.expensetracker.dto.CategoryCreateDTO;
import com.expensetracker.entity.Category;
import com.expensetracker.entity.User;
import com.expensetracker.exception.DuplicateResourceException;
import com.expensetracker.exception.ResourceNotFoundException;
import com.expensetracker.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for Category entity operations
 */
@Service
@Transactional
public class CategoryService {

    private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public CategoryService(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * Create a new category for a user
     */
    public CategoryDTO createCategory(User user, CategoryCreateDTO createDTO) {
        if (categoryRepository.existsByNameAndUser(createDTO.getName(), user)) {
            logger.warn("Attempt to create duplicate category: {} for user: {}", createDTO.getName(), user.getId());
            throw new DuplicateResourceException("Category with name '" + createDTO.getName() + "' already exists");
        }

        Category category = new Category();
        category.setName(createDTO.getName());
        category.setColor(createDTO.getColor());
        category.setIcon(createDTO.getIcon());
        category.setUser(user);

        category = categoryRepository.save(category);
        logger.info("Category created successfully: {}", category.getId());
        return modelMapper.map(category, CategoryDTO.class);
    }

    /**
     * Get all categories for a user
     */
    @Transactional(readOnly = true)
    public List<CategoryDTO> getUserCategories(User user) {
        return categoryRepository.findByUser(user).stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Get category by ID for a user
     */
    @Transactional(readOnly = true)
    public Category getCategoryById(Long id, User user) {
        return categoryRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
    }

    /**
     * Get category DTO by ID for a user
     */
    @Transactional(readOnly = true)
    public CategoryDTO getCategoryDTOById(Long id, User user) {
        Category category = getCategoryById(id, user);
        return modelMapper.map(category, CategoryDTO.class);
    }

    /**
     * Update category
     */
    public CategoryDTO updateCategory(Long id, User user, CategoryCreateDTO updateDTO) {
        Category category = getCategoryById(id, user);
        
        if (!category.getName().equals(updateDTO.getName()) && 
            categoryRepository.existsByNameAndUser(updateDTO.getName(), user)) {
            throw new DuplicateResourceException("Category with name '" + updateDTO.getName() + "' already exists");
        }

        category.setName(updateDTO.getName());
        category.setColor(updateDTO.getColor());
        category.setIcon(updateDTO.getIcon());
        category = categoryRepository.save(category);
        logger.info("Category updated successfully: {}", id);
        return modelMapper.map(category, CategoryDTO.class);
    }

    /**
     * Delete category
     */
    public void deleteCategory(Long id, User user) {
        Category category = getCategoryById(id, user);
        categoryRepository.delete(category);
        logger.info("Category deleted successfully: {}", id);
    }
}
