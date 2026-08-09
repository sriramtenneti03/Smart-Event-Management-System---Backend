package com.expensetracker.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO for Expense information.
 */
public class ExpenseDTO {
    
    private Long id;
    private String description;
    private BigDecimal amount;
    private LocalDate expenseDate;
    private String tags;
    private Long categoryId;
    private String categoryName;
    private Boolean isRecurring;
    private String recurringFrequency;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Constructors
    public ExpenseDTO() {
    }
    
    public ExpenseDTO(Long id, String description, BigDecimal amount, LocalDate expenseDate, Long categoryId, String categoryName) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.expenseDate = expenseDate;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }
    
    public ExpenseDTO(Long id, String description, BigDecimal amount, LocalDate expenseDate, String tags, Long categoryId, String categoryName, Boolean isRecurring, String recurringFrequency, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.expenseDate = expenseDate;
        this.tags = tags;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.isRecurring = isRecurring;
        this.recurringFrequency = recurringFrequency;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public BigDecimal getAmount() {
        return amount;
    }
    
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    
    public LocalDate getExpenseDate() {
        return expenseDate;
    }
    
    public void setExpenseDate(LocalDate expenseDate) {
        this.expenseDate = expenseDate;
    }
    
    public String getTags() {
        return tags;
    }
    
    public void setTags(String tags) {
        this.tags = tags;
    }
    
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
    
    public Boolean getIsRecurring() {
        return isRecurring;
    }
    
    public void setIsRecurring(Boolean isRecurring) {
        this.isRecurring = isRecurring;
    }
    
    public String getRecurringFrequency() {
        return recurringFrequency;
    }
    
    public void setRecurringFrequency(String recurringFrequency) {
        this.recurringFrequency = recurringFrequency;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
