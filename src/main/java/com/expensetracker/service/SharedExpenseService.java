package com.expensetracker.service;

import com.expensetracker.dto.SharedExpenseDTO;
import com.expensetracker.dto.SharedExpenseCreateDTO;
import com.expensetracker.dto.ExpenseSplitDTO;
import com.expensetracker.entity.SharedExpense;
import com.expensetracker.entity.ExpenseSplit;
import com.expensetracker.entity.User;
import com.expensetracker.exception.ResourceNotFoundException;
import com.expensetracker.repository.SharedExpenseRepository;
import com.expensetracker.repository.ExpenseSplitRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for SharedExpense and bill splitting operations
 */
@Service
@Transactional
public class SharedExpenseService {

    private static final Logger logger = LoggerFactory.getLogger(SharedExpenseService.class);

    private final SharedExpenseRepository sharedExpenseRepository;
    private final ExpenseSplitRepository expenseSplitRepository;
    private final ModelMapper modelMapper;

    public SharedExpenseService(SharedExpenseRepository sharedExpenseRepository,
                                ExpenseSplitRepository expenseSplitRepository,
                                ModelMapper modelMapper) {
        this.sharedExpenseRepository = sharedExpenseRepository;
        this.expenseSplitRepository = expenseSplitRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * Create a shared expense with splits
     */
    public SharedExpenseDTO createSharedExpense(User user, SharedExpenseCreateDTO createDTO) {
        SharedExpense sharedExpense = new SharedExpense();
        sharedExpense.setDescription(createDTO.getDescription());
        sharedExpense.setTotalAmount(createDTO.getTotalAmount());
        sharedExpense.setPaidBy(user);

        sharedExpense = sharedExpenseRepository.save(sharedExpense);

        // Create expense splits
        createDTO.getParticipants().forEach(participantDTO -> {
            ExpenseSplit split = new ExpenseSplit();
            split.setExpense(null);  // Not linked to individual expense
            split.setParticipantName(participantDTO.getParticipantName());
            split.setAmount(participantDTO.getAmount());
            split.setPaidBy(false);
            split.setSettled(false);
            expenseSplitRepository.save(split);
        });

        logger.info("Shared expense created successfully: {}", sharedExpense.getId());
        return mapToDTO(sharedExpense);
    }

    /**
     * Get shared expense by ID
     */
    @Transactional(readOnly = true)
    public SharedExpense getSharedExpenseById(Long id, User user) {
        return sharedExpenseRepository.findByIdAndPaidBy(id, user)
                .orElseThrow(() -> new ResourceNotFoundException("Shared expense not found with id: " + id));
    }

    /**
     * Get shared expense DTO by ID
     */
    @Transactional(readOnly = true)
    public SharedExpenseDTO getSharedExpenseDTOById(Long id, User user) {
        SharedExpense sharedExpense = getSharedExpenseById(id, user);
        return mapToDTO(sharedExpense);
    }

    /**
     * Get all shared expenses for a user
     */
    @Transactional(readOnly = true)
    public List<SharedExpenseDTO> getUserSharedExpenses(User user) {
        return sharedExpenseRepository.findByPaidBy(user).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get unsettled shared expenses
     */
    @Transactional(readOnly = true)
    public List<SharedExpenseDTO> getUnsettledExpenses(User user) {
        return sharedExpenseRepository.findByPaidBy(user).stream()
                .filter(exp -> !exp.getIsSettled())
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Mark shared expense as settled
     */
    public SharedExpenseDTO settleSharedExpense(Long id, User user) {
        SharedExpense sharedExpense = getSharedExpenseById(id, user);
        sharedExpense.setIsSettled(true);
        sharedExpense = sharedExpenseRepository.save(sharedExpense);
        logger.info("Shared expense settled: {}", id);
        return mapToDTO(sharedExpense);
    }

    /**
     * Delete shared expense
     */
    public void deleteSharedExpense(Long id, User user) {
        SharedExpense sharedExpense = getSharedExpenseById(id, user);
        sharedExpenseRepository.delete(sharedExpense);
        logger.info("Shared expense deleted: {}", id);
    }

    /**
     * Map SharedExpense entity to DTO
     */
    private SharedExpenseDTO mapToDTO(SharedExpense sharedExpense) {
        SharedExpenseDTO dto = modelMapper.map(sharedExpense, SharedExpenseDTO.class);
        dto.setPaidByName(sharedExpense.getPaidBy().getFullName());
        return dto;
    }
}
