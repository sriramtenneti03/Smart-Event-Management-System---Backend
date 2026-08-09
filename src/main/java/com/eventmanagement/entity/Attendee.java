package com.eventmanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * Attendee entity representing a user's RSVP to an event
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "attendees", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"event_id", "user_id"})
})
public class Attendee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String status; // INTERESTED, ATTENDING, NOT_ATTENDING, MAYBE

    @Column(name = "rsvp_date", nullable = false)
    private LocalDateTime rsvpDate;

    @Column(name = "number_of_guests")
    private Integer numberOfGuests = 0;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(name = "check_in_time")
    private LocalDateTime checkInTime;

    @Column(name = "is_checked_in")
    private Boolean isCheckedIn = false;

    @PrePersist
    protected void onCreate() {
        rsvpDate = LocalDateTime.now();
    }
}
