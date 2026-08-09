package com.eventmanagement.repository;

import com.eventmanagement.entity.Attendee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for Attendee entity
 */
@Repository
public interface AttendeeRepository extends JpaRepository<Attendee, Long> {
    Optional<Attendee> findByEventIdAndUserId(Long eventId, Long userId);
    List<Attendee> findByEventIdOrderByRsvpDateDesc(Long eventId);
    List<Attendee> findByUserIdOrderByRsvpDateDesc(Long userId);
    List<Attendee> findByEventIdAndStatusOrderByRsvpDateDesc(Long eventId, String status);
    
    @Query("SELECT a FROM Attendee a WHERE a.event.id = :eventId AND a.status = 'ATTENDING'")
    List<Attendee> findAttendingByEventId(@Param("eventId") Long eventId);
    
    @Query("SELECT COUNT(a) FROM Attendee a WHERE a.event.id = :eventId AND a.status IN ('ATTENDING', 'INTERESTED')")
    Integer countInterestedAttendees(@Param("eventId") Long eventId);
    
    @Query("SELECT a FROM Attendee a WHERE a.user.id = :userId AND a.status = 'ATTENDING'")
    List<Attendee> findUserAttendingEvents(@Param("userId") Long userId);
}
