package com.eventmanagement.repository;

import com.eventmanagement.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository for Event entity
 */
@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByOrganizerIdOrderByEventDateDesc(Long organizerId);
    List<Event> findByStatusOrderByEventDateDesc(String status);
    List<Event> findByIsPublicTrueOrderByEventDateDesc();
    List<Event> findByCategoryOrderByEventDateDesc(String category);
    
    @Query("SELECT e FROM Event e WHERE e.city = :city AND e.isPublic = true ORDER BY e.eventDate DESC")
    List<Event> findEventsByCity(@Param("city") String city);
    
    @Query("SELECT e FROM Event e WHERE e.eventDate BETWEEN :startDate AND :endDate ORDER BY e.eventDate ASC")
    List<Event> findEventsByDateRange(@Param("startDate") LocalDateTime startDate, 
                                      @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT e FROM Event e WHERE e.title LIKE %:keyword% OR e.description LIKE %:keyword% ORDER BY e.eventDate DESC")
    List<Event> searchEvents(@Param("keyword") String keyword);
}
