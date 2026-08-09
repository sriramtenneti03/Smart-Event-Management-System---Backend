package com.expensetracker.dto;

import java.math.BigDecimal;

/**
 * DTO for category-wise expense breakdown.
 */
public class CategoryExpenseDTO {
    
    private Long categoryId;
    private String categoryName;
    private BigDecimal totalAmount;
    private Long expenseCount;
    private BigDecimal percentage;
    
    // Constructors
    public CategoryExpenseDTO() {
    }
    
    public CategoryExpenseDTO(Long categoryId, String categoryName, BigDecimal totalAmount, Long expenseCount) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.totalAmount = totalAmount;
        this.expenseCount = expenseCount;
    }
    
    public CategoryExpenseDTO(Long categoryId, String categoryName, BigDecimal totalAmount, Long expenseCount, BigDecimal percentage) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.totalAmount = totalAmount;
        this.expenseCount = expenseCount;
        this.percentage = percentage;
    }
    
    // Getters and Setters
    public Long getCategoryId() {
        return categoryId;
    }
    
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
    
    public String getCategoryName() {
        return categoryName;
    }
    
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }
    
    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
    
    public Long getExpenseCount() {
        return expenseCount;
    }
    
    public void setExpenseCount(Long expenseCount) {
        this.expenseCount = expenseCount;
    }
    
    public BigDecimal getPercentage() {
        return percentage;
    }
    
    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
    }
}
