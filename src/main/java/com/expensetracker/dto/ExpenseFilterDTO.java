package com.expensetracker.dto;

import jakarta.validation.constraints.Min;
import java.time.LocalDate;

/**
 * DTO for filtering and paginating expense search.
 */
public class ExpenseFilterDTO {
    
    private LocalDate startDate;
    private LocalDate endDate;
    private Long categoryId;
    private String tags;
    
    @Min(value = 0, message = "Page must be at least 0")
    private Integer page = 0;
    
    @Min(value = 1, message = "Page size must be at least 1")
    private Integer pageSize = 20;
    
    private String sortBy = "expenseDate";
    private String sortOrder = "DESC";
    
    // Constructors
    public ExpenseFilterDTO() {
    }
    
    public ExpenseFilterDTO(LocalDate startDate, LocalDate endDate, Long categoryId) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.categoryId = categoryId;
    }
    
    public ExpenseFilterDTO(LocalDate startDate, LocalDate endDate, Long categoryId, String tags, Integer page, Integer pageSize, String sortBy, String sortOrder) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.categoryId = categoryId;
        this.tags = tags;
        this.page = page;
        this.pageSize = pageSize;
        this.sortBy = sortBy;
        this.sortOrder = sortOrder;
    }
    
    // Getters and Setters
    public LocalDate getStartDate() {
        return startDate;
    }
    
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    
    public LocalDate getEndDate() {
        return endDate;
    }
    
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    
    public Long getCategoryId() {
        return categoryId;
    }
    
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
    
    public String getTags() {
        return tags;
    }
    
    public void setTags(String tags) {
        this.tags = tags;
    }
    
    public Integer getPage() {
        return page;
    }
    
    public void setPage(Integer page) {
        this.page = page;
    }
    
    public Integer getPageSize() {
        return pageSize;
    }
    
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
    
    public String getSortBy() {
        return sortBy;
    }
    
    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }
    
    public String getSortOrder() {
        return sortOrder;
    }
    
    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }
}
