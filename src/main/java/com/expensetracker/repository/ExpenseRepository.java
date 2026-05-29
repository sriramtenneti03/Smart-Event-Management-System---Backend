package com.expensetracker.repository;

import com.expensetracker.entity.Expense;
import com.expensetracker.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository for Expense entity
 */
@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    
    Optional<Expense> findByIdAndUser(Long id, User user);
    
    Page<Expense> findByUser(User user, Pageable pageable);
    
    List<Expense> findByUserAndExpenseDateBetween(User user, LocalDate startDate, LocalDate endDate);
    
    Page<Expense> findByUserAndExpenseDateBetween(User user, LocalDate startDate, LocalDate endDate, Pageable pageable);
    
    @Query("SELECT e FROM Expense e WHERE e.user = :user AND e.expenseDate BETWEEN :startDate AND :endDate " +
           "AND (:categoryId IS NULL OR e.category.id = :categoryId) " +
           "AND (:tags IS NULL OR e.tags LIKE %:tags%)")
    Page<Expense> findExpensesWithFilters(@Param("user") User user,
                                          @Param("startDate") LocalDate startDate,
                                          @Param("endDate") LocalDate endDate,
                                          @Param("categoryId") Long categoryId,
                                          @Param("tags") String tags,
                                          Pageable pageable);
    
    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expense e WHERE e.user = :user " +
           "AND e.expenseDate BETWEEN :startDate AND :endDate")
    BigDecimal getTotalExpenseAmount(@Param("user") User user,
                                     @Param("startDate") LocalDate startDate,
                                     @Param("endDate") LocalDate endDate);
    
    @Query("SELECT COUNT(e) FROM Expense e WHERE e.user = :user " +
           "AND e.expenseDate BETWEEN :startDate AND :endDate")
    Long getExpenseCount(@Param("user") User user,
                        @Param("startDate") LocalDate startDate,
                        @Param("endDate") LocalDate endDate);
    
    @Query("SELECT e.category.name, COALESCE(SUM(e.amount), 0) FROM Expense e WHERE e.user = :user " +
           "AND e.expenseDate BETWEEN :startDate AND :endDate GROUP BY e.category.name")
    List<Object[]> getCategoryWiseExpenses(@Param("user") User user,
                                           @Param("startDate") LocalDate startDate,
                                           @Param("endDate") LocalDate endDate);
}
