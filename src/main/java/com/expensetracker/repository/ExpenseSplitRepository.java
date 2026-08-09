package com.expensetracker.repository;

import com.expensetracker.entity.Expense;
import com.expensetracker.entity.ExpenseSplit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * Repository for ExpenseSplit entity
 */
@Repository
public interface ExpenseSplitRepository extends JpaRepository<ExpenseSplit, Long> {
    
    List<ExpenseSplit> findByExpense(Expense expense);
    
    Optional<ExpenseSplit> findByIdAndExpense(Long id, Expense expense);
    
    List<ExpenseSplit> findByExpenseAndSettled(Expense expense, Boolean settled);
}
