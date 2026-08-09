package com.expensetracker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO for creating a new category.
 */
public class CategoryCreateDTO {
    
    @NotBlank(message = "Category name is required")
    @Size(min = 1, max = 50, message = "Category name must be between 1 and 50 characters")
    private String name;
    
    @NotBlank(message = "Color is required")
    @Size(max = 7, message = "Color code must be at most 7 characters")
    private String color;
    
    @NotBlank(message = "Icon is required")
    @Size(max = 50, message = "Icon must be at most 50 characters")
    private String icon;
    
    // Constructors
    public CategoryCreateDTO() {
    }
    
    public CategoryCreateDTO(String name, String color, String icon) {
        this.name = name;
        this.color = color;
        this.icon = icon;
    }
    
    // Getters and Setters
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getColor() {
        return color;
    }
    
    public void setColor(String color) {
        this.color = color;
    }
    
    public String getIcon() {
        return icon;
    }
    
    public void setIcon(String icon) {
        this.icon = icon;
    }
}
