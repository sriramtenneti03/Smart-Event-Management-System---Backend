package com.eventmanagement.repository;

import com.eventmanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

/**
 * Repository for User entity
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    List<User> findByIsActiveTrue();
    List<User> findByCity(String city);
    
    @Query("SELECT u FROM User u WHERE u.email LIKE %:keyword% OR u.fullName LIKE %:keyword%")
    List<User> searchUsers(@Param("keyword") String keyword);
}
