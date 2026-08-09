package com.eventmanagement.repository;

import com.eventmanagement.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for Review entity
 */
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByEventIdOrderByCreatedAtDesc(Long eventId);
    Optional<Review> findByEventIdAndReviewerId(Long eventId, Long reviewerId);
    
    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.event.id = :eventId")
    Double getAverageRatingByEventId(@Param("eventId") Long eventId);
    
    @Query("SELECT COUNT(r) FROM Review r WHERE r.event.id = :eventId")
    Integer countReviewsByEventId(@Param("eventId") Long eventId);
    
    List<Review> findByReviewerIdOrderByCreatedAtDesc(Long reviewerId);
}
