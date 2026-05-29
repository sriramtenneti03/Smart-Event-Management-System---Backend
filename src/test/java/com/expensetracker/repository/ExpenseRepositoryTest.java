package com.expensetracker.repository;

import com.expensetracker.entity.User;
import com.expensetracker.entity.Category;
import com.expensetracker.entity.Expense;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for ExpenseRepository
 */
@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.yml")
class ExpenseRepositoryTest {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private User testUser;
    private Category testCategory;
    private Expense testExpense;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setEmail("test@example.com");
        testUser.setFirstName("John");
        testUser.setLastName("Doe");
        testUser.setPassword("password");
        testUser = userRepository.save(testUser);

        testCategory = new Category();
        testCategory.setName("Food");
        testCategory.setColor("#FF0000");
        testCategory.setUser(testUser);
        testCategory = categoryRepository.save(testCategory);

        testExpense = new Expense();
        testExpense.setDescription("Lunch");
        testExpense.setAmount(new BigDecimal("50.00"));
        testExpense.setExpenseDate(LocalDate.now());
        testExpense.setUser(testUser);
        testExpense.setCategory(testCategory);
        testExpense = expenseRepository.save(testExpense);
    }

    @Test
    void testFindByIdAndUserSuccess() {
        Optional<Expense> result = expenseRepository.findByIdAndUser(testExpense.getId(), testUser);

        assertTrue(result.isPresent());
        assertEquals("Lunch", result.get().getDescription());
    }

    @Test
    void testFindByIdAndUserNotFound() {
        Optional<Expense> result = expenseRepository.findByIdAndUser(999L, testUser);

        assertFalse(result.isPresent());
    }

    @Test
    void testGetTotalExpenseAmount() {
        LocalDate startDate = LocalDate.now().minusDays(1);
        LocalDate endDate = LocalDate.now().plusDays(1);

        BigDecimal total = expenseRepository.getTotalExpenseAmount(testUser, startDate, endDate);

        assertNotNull(total);
        assertEquals(0, new BigDecimal("50.00").compareTo(total));
    }

    @Test
    void testGetExpenseCount() {
        LocalDate startDate = LocalDate.now().minusDays(1);
        LocalDate endDate = LocalDate.now().plusDays(1);

        Long count = expenseRepository.getExpenseCount(testUser, startDate, endDate);

        assertNotNull(count);
        assertEquals(1L, count);
    }
}
