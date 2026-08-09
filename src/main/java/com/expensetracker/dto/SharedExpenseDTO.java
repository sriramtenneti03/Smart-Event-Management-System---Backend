package com.expensetracker.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for SharedExpense information.
 */
public class SharedExpenseDTO {
    
    private Long id;
    private String description;
    private BigDecimal totalAmount;
    private String paidByName;
    private Boolean isSettled;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<ExpenseSplitDTO> splits;
    
    // Constructors
    public SharedExpenseDTO() {
    }
    
    public SharedExpenseDTO(Long id, String description, BigDecimal totalAmount, String paidByName, Boolean isSettled) {
        this.id = id;
        this.description = description;
        this.totalAmount = totalAmount;
        this.paidByName = paidByName;
        this.isSettled = isSettled;
    }
    
    public SharedExpenseDTO(Long id, String description, BigDecimal totalAmount, String paidByName, Boolean isSettled, LocalDateTime createdAt, LocalDateTime updatedAt, List<ExpenseSplitDTO> splits) {
        this.id = id;
        this.description = description;
        this.totalAmount = totalAmount;
        this.paidByName = paidByName;
        this.isSettled = isSettled;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.splits = splits;
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
    
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }
    
    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
    
    public String getPaidByName() {
        return paidByName;
    }
    
    public void setPaidByName(String paidByName) {
        this.paidByName = paidByName;
    }
    
    public Boolean getIsSettled() {
        return isSettled;
    }
    
    public void setIsSettled(Boolean isSettled) {
        this.isSettled = isSettled;
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
    
    public List<ExpenseSplitDTO> getSplits() {
        return splits;
    }
    
    public void setSplits(List<ExpenseSplitDTO> splits) {
        this.splits = splits;
    }
}
