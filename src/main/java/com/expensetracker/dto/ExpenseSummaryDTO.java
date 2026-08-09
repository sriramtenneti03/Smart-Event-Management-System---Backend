package com.expensetracker.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO for expense summary statistics.
 */
public class ExpenseSummaryDTO {
    
    private BigDecimal totalExpenses;
    private BigDecimal totalAmount;
    private BigDecimal averageExpense;
    private BigDecimal maxExpense;
    private BigDecimal minExpense;
    private LocalDate periodStart;
    private LocalDate periodEnd;
    
    // Constructors
    public ExpenseSummaryDTO() {
    }
    
    public ExpenseSummaryDTO(BigDecimal totalExpenses, BigDecimal totalAmount, BigDecimal averageExpense, BigDecimal maxExpense, BigDecimal minExpense) {
        this.totalExpenses = totalExpenses;
        this.totalAmount = totalAmount;
        this.averageExpense = averageExpense;
        this.maxExpense = maxExpense;
        this.minExpense = minExpense;
    }
    
    public ExpenseSummaryDTO(BigDecimal totalExpenses, BigDecimal totalAmount, BigDecimal averageExpense, BigDecimal maxExpense, BigDecimal minExpense, LocalDate periodStart, LocalDate periodEnd) {
        this.totalExpenses = totalExpenses;
        this.totalAmount = totalAmount;
        this.averageExpense = averageExpense;
        this.maxExpense = maxExpense;
        this.minExpense = minExpense;
        this.periodStart = periodStart;
        this.periodEnd = periodEnd;
    }
    
    // Getters and Setters
    public BigDecimal getTotalExpenses() {
        return totalExpenses;
    }
    
    public void setTotalExpenses(BigDecimal totalExpenses) {
        this.totalExpenses = totalExpenses;
    }
    
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }
    
    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
    
    public BigDecimal getAverageExpense() {
        return averageExpense;
    }
    
    public void setAverageExpense(BigDecimal averageExpense) {
        this.averageExpense = averageExpense;
    }
    
    public BigDecimal getMaxExpense() {
        return maxExpense;
    }
    
    public void setMaxExpense(BigDecimal maxExpense) {
        this.maxExpense = maxExpense;
    }
    
    public BigDecimal getMinExpense() {
        return minExpense;
    }
    
    public void setMinExpense(BigDecimal minExpense) {
        this.minExpense = minExpense;
    }
    
    public LocalDate getPeriodStart() {
        return periodStart;
    }
    
    public void setPeriodStart(LocalDate periodStart) {
        this.periodStart = periodStart;
    }
    
    public LocalDate getPeriodEnd() {
        return periodEnd;
    }
    
    public void setPeriodEnd(LocalDate periodEnd) {
        this.periodEnd = periodEnd;
    }
}
