package com.expensetracker.repository;

import com.expensetracker.entity.SharedExpense;
import com.expensetracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * Repository for SharedExpense entity
 */
@Repository
public interface SharedExpenseRepository extends JpaRepository<SharedExpense, Long> {
    
    List<SharedExpense> findByPaidBy(User user);
    
    Optional<SharedExpense> findByIdAndPaidBy(Long id, User user);
    
    List<SharedExpense> findByIsSettled(Boolean isSettled);
}
