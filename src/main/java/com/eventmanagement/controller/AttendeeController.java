package com.eventmanagement.controller;

import com.eventmanagement.entity.Attendee;
import com.eventmanagement.service.AttendeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Attendee/RSVP operations
 */
@RestController
@RequestMapping("/api/attendees")
@RequiredArgsConstructor
public class AttendeeController {

    private final AttendeeService attendeeService;

    @PostMapping("/rsvp")
    public ResponseEntity<Void> rsvpEvent(
            @RequestParam Long eventId,
            @RequestParam Long userId,
            @RequestParam String status,
            @RequestParam(required = false) Integer numberOfGuests) {
        attendeeService.rsvpEvent(eventId, userId, status, numberOfGuests);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/cancel-rsvp")
    public ResponseEntity<Void> cancelRSVP(
            @RequestParam Long eventId,
            @RequestParam Long userId) {
        attendeeService.cancelRSVP(eventId, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<Attendee>> getEventAttendees(@PathVariable Long eventId) {
        List<Attendee> attendees = attendeeService.getEventAttendees(eventId);
        return ResponseEntity.ok(attendees);
    }

    @GetMapping("/event/{eventId}/status/{status}")
    public ResponseEntity<List<Attendee>> getAttendeesByStatus(
            @PathVariable Long eventId,
            @PathVariable String status) {
        List<Attendee> attendees = attendeeService.getEventAttendeesByStatus(eventId, status);
        return ResponseEntity.ok(attendees);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Attendee>> getUserAttendingEvents(@PathVariable Long userId) {
        List<Attendee> events = attendeeService.getUserAttendingEvents(userId);
        return ResponseEntity.ok(events);
    }

    @GetMapping("/count/{eventId}")
    public ResponseEntity<Integer> getAttendeeCount(@PathVariable Long eventId) {
        Integer count = attendeeService.getEventAttendeeCount(eventId);
        return ResponseEntity.ok(count);
    }

    @PostMapping("/{attendeeId}/check-in")
    public ResponseEntity<Void> checkInAttendee(@PathVariable Long attendeeId) {
        attendeeService.checkInAttendee(attendeeId);
        return ResponseEntity.noContent().build();
    }
}
