package com.expensetracker.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * DTO for creating a shared expense.
 */
public class SharedExpenseCreateDTO {
    
    @NotBlank(message = "Description is required")
    private String description;
    
    @NotNull(message = "Total amount is required")
    @DecimalMin(value = "0.01", inclusive = true, message = "Total amount must be greater than 0")
    private BigDecimal totalAmount;
    
    @NotNull(message = "Participants list is required")
    private List<ExpenseSplitCreateDTO> participants;
    
    // Constructors
    public SharedExpenseCreateDTO() {
    }
    
    public SharedExpenseCreateDTO(String description, BigDecimal totalAmount, List<ExpenseSplitCreateDTO> participants) {
        this.description = description;
        this.totalAmount = totalAmount;
        this.participants = participants;
    }
    
    // Getters and Setters
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
    
    public List<ExpenseSplitCreateDTO> getParticipants() {
        return participants;
    }
    
    public void setParticipants(List<ExpenseSplitCreateDTO> participants) {
        this.participants = participants;
    }
}
