package com.expensetracker.service;

import com.expensetracker.dto.*;
import com.expensetracker.entity.Expense;
import com.expensetracker.entity.User;
import com.expensetracker.entity.Category;
import com.expensetracker.exception.ResourceNotFoundException;
import com.expensetracker.repository.ExpenseRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service class for Expense entity operations
 */
@Service
@Transactional
public class ExpenseService {

    private static final Logger logger = LoggerFactory.getLogger(ExpenseService.class);

    private final ExpenseRepository expenseRepository;
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    public ExpenseService(ExpenseRepository expenseRepository, CategoryService categoryService, ModelMapper modelMapper) {
        this.expenseRepository = expenseRepository;
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }

    /**
     * Create a new expense
     */
    public ExpenseDTO createExpense(User user, ExpenseDTO expenseDTO) {
        Category category = categoryService.getCategoryById(expenseDTO.getCategoryId(), user);

        Expense expense = modelMapper.map(expenseDTO, Expense.class);
        expense.setUser(user);
        expense.setCategory(category);

        expense = expenseRepository.save(expense);
        logger.info("Expense created successfully: {}", expense.getId());
        return mapToDTO(expense);
    }

    /**
     * Get expense by ID
     */
    @Transactional(readOnly = true)
    public Expense getExpenseById(Long id, User user) {
        return expenseRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found with id: " + id));
    }

    /**
     * Get expense DTO by ID
     */
    @Transactional(readOnly = true)
    public ExpenseDTO getExpenseDTOById(Long id, User user) {
        Expense expense = getExpenseById(id, user);
        return mapToDTO(expense);
    }

    /**
     * Get paginated expenses for user
     */
    @Transactional(readOnly = true)
    public Page<ExpenseDTO> getUserExpenses(User user, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("expenseDate").descending());
        return expenseRepository.findByUser(user, pageable)
                .map(this::mapToDTO);
    }

    /**
     * Get expenses with filters
     */
    @Transactional(readOnly = true)
    public Page<ExpenseDTO> getExpensesWithFilters(User user, ExpenseFilterDTO filterDTO) {
        LocalDate startDate = filterDTO.getStartDate() != null ? filterDTO.getStartDate() : LocalDate.now().minusMonths(1);
        LocalDate endDate = filterDTO.getEndDate() != null ? filterDTO.getEndDate() : LocalDate.now();

        Sort.Direction direction = "DESC".equalsIgnoreCase(filterDTO.getSortOrder()) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(filterDTO.getPage(), filterDTO.getPageSize(), Sort.by(direction, filterDTO.getSortBy()));

        return expenseRepository.findExpensesWithFilters(user, startDate, endDate, filterDTO.getCategoryId(), filterDTO.getTags(), pageable)
                .map(this::mapToDTO);
    }

    /**
     * Update expense
     */
    public ExpenseDTO updateExpense(Long id, User user, ExpenseDTO expenseDTO) {
        Expense expense = getExpenseById(id, user);
        Category category = categoryService.getCategoryById(expenseDTO.getCategoryId(), user);

        expense.setDescription(expenseDTO.getDescription());
        expense.setAmount(expenseDTO.getAmount());
        expense.setExpenseDate(expenseDTO.getExpenseDate());
        expense.setTags(expenseDTO.getTags());
        expense.setCategory(category);

        expense = expenseRepository.save(expense);
        logger.info("Expense updated successfully: {}", id);
        return mapToDTO(expense);
    }

    /**
     * Delete expense
     */
    public void deleteExpense(Long id, User user) {
        Expense expense = getExpenseById(id, user);
        expenseRepository.delete(expense);
        logger.info("Expense deleted successfully: {}", id);
    }

    /**
     * Get monthly spending summary
     */
    @Transactional(readOnly = true)
    public ExpenseSummaryDTO getMonthlySummary(User user, YearMonth month) {
        LocalDate startDate = month.atDay(1);
        LocalDate endDate = month.atEndOfMonth();

        BigDecimal totalAmount = expenseRepository.getTotalExpenseAmount(user, startDate, endDate);
        Long expenseCount = expenseRepository.getExpenseCount(user, startDate, endDate);

        List<Expense> expenses = expenseRepository.findByUserAndExpenseDateBetween(user, startDate, endDate);

        BigDecimal avgExpense = BigDecimal.ZERO;
        BigDecimal maxExpense = BigDecimal.ZERO;
        BigDecimal minExpense = BigDecimal.ZERO;

        if (!expenses.isEmpty()) {
            avgExpense = totalAmount.divide(BigDecimal.valueOf(expenseCount), 2, RoundingMode.HALF_UP);
            maxExpense = expenses.stream().map(Expense::getAmount).max(BigDecimal::compareTo).orElse(BigDecimal.ZERO);
            minExpense = expenses.stream().map(Expense::getAmount).min(BigDecimal::compareTo).orElse(BigDecimal.ZERO);
        }

        return new ExpenseSummaryDTO(
                BigDecimal.valueOf(expenseCount != null ? expenseCount : 0),
                totalAmount != null ? totalAmount : BigDecimal.ZERO,
                avgExpense,
                maxExpense,
                minExpense,
                startDate,
                endDate
        );
    }

    /**
     * Get category-wise expense breakdown
     */
    @Transactional(readOnly = true)
    public List<CategoryExpenseDTO> getCategoryWiseExpenses(User user, LocalDate startDate, LocalDate endDate) {
        List<Object[]> results = expenseRepository.getCategoryWiseExpenses(user, startDate, endDate);

        if (results.isEmpty()) {
            return Collections.emptyList();
        }

        BigDecimal totalAmount = results.stream()
                .map(row -> (BigDecimal) row[1])
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return results.stream()
                .map(row -> {
                    String categoryName = (String) row[0];
                    BigDecimal categoryAmount = (BigDecimal) row[1];
                    double percentage = categoryAmount.divide(totalAmount, 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100)).doubleValue();

                    return new CategoryExpenseDTO(
                            null,
                            categoryName,
                            categoryAmount,
                            null,
                            BigDecimal.valueOf(percentage)
                    );
                })
                .collect(Collectors.toList());
    }

    /**
     * Get spending trends
     */
    @Transactional(readOnly = true)
    public Map<String, BigDecimal> getSpendingTrends(User user, int months) {
        Map<String, BigDecimal> trends = new LinkedHashMap<>();

        for (int i = months - 1; i >= 0; i--) {
            YearMonth month = YearMonth.now().minusMonths(i);
            LocalDate startDate = month.atDay(1);
            LocalDate endDate = month.atEndOfMonth();
            BigDecimal amount = expenseRepository.getTotalExpenseAmount(user, startDate, endDate);
            trends.put(month.toString(), amount);
        }

        return trends;
    }

    /**
     * Map Expense entity to DTO
     */
    private ExpenseDTO mapToDTO(Expense expense) {
        ExpenseDTO dto = modelMapper.map(expense, ExpenseDTO.class);
        dto.setCategoryName(expense.getCategory().getName());
        return dto;
    }
}
