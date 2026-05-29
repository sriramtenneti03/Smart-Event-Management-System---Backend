package com.expensetracker.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * ExpenseSplit entity representing individual splits of a shared expense.
 */
@Entity
@Table(name = "expense_splits")
public class ExpenseSplit {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "Expense is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "expense_id", nullable = false)
    private Expense expense;
    
    @NotBlank(message = "Participant name is required")
    @Column(nullable = false)
    private String participantName;
    
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", inclusive = true, message = "Amount must be greater than 0")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;
    
    @Column(nullable = false)
    private Boolean paidBy = false;
    
    @Column(nullable = false)
    private Boolean settled = false;
    
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(nullable = false)
    private LocalDateTime updatedAt;
    
    // Constructors
    public ExpenseSplit() {
    }
    
    public ExpenseSplit(Expense expense, String participantName, BigDecimal amount) {
        this.expense = expense;
        this.participantName = participantName;
        this.amount = amount;
        this.paidBy = false;
        this.settled = false;
    }
    
    public ExpenseSplit(Long id, Expense expense, String participantName, BigDecimal amount, Boolean paidBy, Boolean settled, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.expense = expense;
        this.participantName = participantName;
        this.amount = amount;
        this.paidBy = paidBy;
        this.settled = settled;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
    // JPA lifecycle callbacks
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (paidBy == null) {
            paidBy = false;
        }
        if (settled == null) {
            settled = false;
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
    
    public Expense getExpense() {
        return expense;
    }
    
    public void setExpense(Expense expense) {
        this.expense = expense;
    }
    
    public String getParticipantName() {
        return participantName;
    }
    
    public void setParticipantName(String participantName) {
        this.participantName = participantName;
    }
    
    public BigDecimal getAmount() {
        return amount;
    }
    
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    
    public Boolean getPaidBy() {
        return paidBy;
    }
    
    public void setPaidBy(Boolean paidBy) {
        this.paidBy = paidBy;
    }
    
    public Boolean getSettled() {
        return settled;
    }
    
    public void setSettled(Boolean settled) {
        this.settled = settled;
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
