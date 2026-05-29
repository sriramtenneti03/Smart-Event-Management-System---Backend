package com.expensetracker.service;

import com.expensetracker.dto.ExpenseDTO;
import com.expensetracker.entity.Expense;
import com.expensetracker.entity.User;
import com.expensetracker.entity.Category;
import com.expensetracker.exception.ResourceNotFoundException;
import com.expensetracker.repository.ExpenseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * Unit tests for ExpenseService
 */
@ExtendWith(MockitoExtension.class)
class ExpenseServiceTest {

    @Mock
    private ExpenseRepository expenseRepository;

    @Mock
    private CategoryService categoryService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ExpenseService expenseService;

    private User testUser;
    private Category testCategory;
    private Expense testExpense;
    private ExpenseDTO testExpenseDTO;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .id(1L)
                .email("test@example.com")
                .firstName("John")
                .lastName("Doe")
                .build();

        testCategory = Category.builder()
                .id(1L)
                .name("Food")
                .color("#FF0000")
                .user(testUser)
                .build();

        testExpense = Expense.builder()
                .id(1L)
                .description("Lunch")
                .amount(new BigDecimal("50.00"))
                .expenseDate(LocalDate.now())
                .user(testUser)
                .category(testCategory)
                .build();

        testExpenseDTO = ExpenseDTO.builder()
                .id(1L)
                .description("Lunch")
                .amount(new BigDecimal("50.00"))
                .expenseDate(LocalDate.now())
                .categoryId(1L)
                .categoryName("Food")
                .build();
    }

    @Test
    void testCreateExpenseSuccess() {
        when(categoryService.getCategoryById(anyLong(), any(User.class))).thenReturn(testCategory);
        when(modelMapper.map(any(ExpenseDTO.class), eq(Expense.class))).thenReturn(testExpense);
        when(expenseRepository.save(any(Expense.class))).thenReturn(testExpense);
        when(modelMapper.map(any(Expense.class), eq(ExpenseDTO.class))).thenReturn(testExpenseDTO);

        ExpenseDTO result = expenseService.createExpense(testUser, testExpenseDTO);

        assertNotNull(result);
        assertEquals("Lunch", result.getDescription());
        verify(expenseRepository, times(1)).save(any(Expense.class));
    }

    @Test
    void testGetExpenseByIdSuccess() {
        when(expenseRepository.findByIdAndUser(anyLong(), any(User.class))).thenReturn(Optional.of(testExpense));

        Expense result = expenseService.getExpenseById(1L, testUser);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testGetExpenseByIdNotFound() {
        when(expenseRepository.findByIdAndUser(anyLong(), any(User.class))).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
            () -> expenseService.getExpenseById(999L, testUser));
    }

    @Test
    void testGetUserExpensesSuccess() {
        List<Expense> expenses = new ArrayList<>();
        expenses.add(testExpense);
        Page<Expense> page = new PageImpl<>(expenses);

        when(expenseRepository.findByUser(any(User.class), any(Pageable.class))).thenReturn(page);
        when(modelMapper.map(any(Expense.class), eq(ExpenseDTO.class))).thenReturn(testExpenseDTO);

        Page<ExpenseDTO> result = expenseService.getUserExpenses(testUser, 0, 20);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
    }

    @Test
    void testDeleteExpenseSuccess() {
        when(expenseRepository.findByIdAndUser(anyLong(), any(User.class))).thenReturn(Optional.of(testExpense));

        assertDoesNotThrow(() -> expenseService.deleteExpense(1L, testUser));
        verify(expenseRepository, times(1)).delete(any(Expense.class));
    }
}
