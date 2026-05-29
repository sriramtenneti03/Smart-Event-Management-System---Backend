package com.expensetracker.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * DTO for creating an expense split.
 */
public class ExpenseSplitCreateDTO {
    
    @NotBlank(message = "Participant name is required")
    private String participantName;
    
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", inclusive = true, message = "Amount must be greater than 0")
    private BigDecimal amount;
    
    // Constructors
    public ExpenseSplitCreateDTO() {
    }
    
    public ExpenseSplitCreateDTO(String participantName, BigDecimal amount) {
        this.participantName = participantName;
        this.amount = amount;
    }
    
    // Getters and Setters
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
}
