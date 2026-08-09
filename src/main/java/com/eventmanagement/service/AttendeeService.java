package com.eventmanagement.service;

import com.eventmanagement.entity.Attendee;
import com.eventmanagement.entity.Event;
import com.eventmanagement.entity.User;
import com.eventmanagement.exception.ResourceNotFoundException;
import com.eventmanagement.repository.AttendeeRepository;
import com.eventmanagement.repository.EventRepository;
import com.eventmanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service layer for Attendee/RSVP operations
 */
@Service
@RequiredArgsConstructor
@Transactional
public class AttendeeService {

    private final AttendeeRepository attendeeRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public void rsvpEvent(Long eventId, Long userId, String status, Integer numberOfGuests) {
        Event event = eventRepository.findById(eventId)
            .orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + eventId));
        
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        
        // Check if already RSVP'd
        if (attendeeRepository.findByEventIdAndUserId(eventId, userId).isPresent()) {
            // Update existing RSVP
            Attendee attendee = attendeeRepository.findByEventIdAndUserId(eventId, userId).get();
            attendee.setStatus(status);
            attendee.setNumberOfGuests(numberOfGuests != null ? numberOfGuests : 0);
            attendeeRepository.save(attendee);
        } else {
            // Create new RSVP
            Attendee attendee = new Attendee();
            attendee.setEvent(event);
            attendee.setUser(user);
            attendee.setStatus(status);
            attendee.setNumberOfGuests(numberOfGuests != null ? numberOfGuests : 0);
            attendeeRepository.save(attendee);
        }
    }

    public void cancelRSVP(Long eventId, Long userId) {
        Attendee attendee = attendeeRepository.findByEventIdAndUserId(eventId, userId)
            .orElseThrow(() -> new ResourceNotFoundException("RSVP not found"));
        attendeeRepository.delete(attendee);
    }

    public List<Attendee> getEventAttendees(Long eventId) {
        return attendeeRepository.findByEventIdOrderByRsvpDateDesc(eventId);
    }

    public List<Attendee> getUserAttendingEvents(Long userId) {
        return attendeeRepository.findUserAttendingEvents(userId);
    }

    public List<Attendee> getEventAttendeesByStatus(Long eventId, String status) {
        return attendeeRepository.findByEventIdAndStatusOrderByRsvpDateDesc(eventId, status);
    }

    public void checkInAttendee(Long attendeeId) {
        Attendee attendee = attendeeRepository.findById(attendeeId)
            .orElseThrow(() -> new ResourceNotFoundException("Attendee not found with id: " + attendeeId));
        attendee.setIsCheckedIn(true);
        attendee.setCheckInTime(LocalDateTime.now());
        attendeeRepository.save(attendee);
    }

    public Integer getEventAttendeeCount(Long eventId) {
        List<Attendee> attendees = getEventAttendees(eventId);
        return attendees.size();
    }
}
