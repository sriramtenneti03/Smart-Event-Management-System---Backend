package com.expensetracker.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO for ExpenseSplit information.
 */
public class ExpenseSplitDTO {
    
    private Long id;
    private String participantName;
    private BigDecimal amount;
    private Boolean paidBy;
    private Boolean settled;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Constructors
    public ExpenseSplitDTO() {
    }
    
    public ExpenseSplitDTO(Long id, String participantName, BigDecimal amount, Boolean paidBy, Boolean settled) {
        this.id = id;
        this.participantName = participantName;
        this.amount = amount;
        this.paidBy = paidBy;
        this.settled = settled;
    }
    
    public ExpenseSplitDTO(Long id, String participantName, BigDecimal amount, Boolean paidBy, Boolean settled, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.participantName = participantName;
        this.amount = amount;
        this.paidBy = paidBy;
        this.settled = settled;
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
