package com.expensetracker.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * SharedExpense entity representing expenses that are shared among multiple users.
 */
@Entity
@Table(name = "shared_expenses")
public class SharedExpense {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Shared expense description is required")
    @Column(nullable = false)
    private String description;
    
    @NotNull(message = "Total amount is required")
    @DecimalMin(value = "0.01", inclusive = true, message = "Total amount must be greater than 0")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;
    
    @NotNull(message = "User who paid is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paid_by_user_id", nullable = false)
    private User paidBy;
    
    @Column(nullable = false)
    private Boolean isSettled = false;
    
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(nullable = false)
    private LocalDateTime updatedAt;
    
    // Constructors
    public SharedExpense() {
    }
    
    public SharedExpense(String description, BigDecimal totalAmount, User paidBy) {
        this.description = description;
        this.totalAmount = totalAmount;
        this.paidBy = paidBy;
        this.isSettled = false;
    }
    
    public SharedExpense(Long id, String description, BigDecimal totalAmount, User paidBy, Boolean isSettled, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.description = description;
        this.totalAmount = totalAmount;
        this.paidBy = paidBy;
        this.isSettled = isSettled;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
    // JPA lifecycle callbacks
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (isSettled == null) {
            isSettled = false;
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
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
    
    public User getPaidBy() {
        return paidBy;
    }
    
    public void setPaidBy(User paidBy) {
        this.paidBy = paidBy;
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
}
