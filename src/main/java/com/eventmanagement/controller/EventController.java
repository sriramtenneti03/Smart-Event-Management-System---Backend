package com.eventmanagement.controller;

import com.eventmanagement.dto.CreateEventDTO;
import com.eventmanagement.dto.EventDTO;
import com.eventmanagement.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * REST Controller for Event operations
 */
@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
@Validated
public class EventController {

    private final EventService eventService;

    @PostMapping
    public ResponseEntity<EventDTO> createEvent(
            @RequestParam Long organizerId,
            @Valid @RequestBody CreateEventDTO createEventDTO) {
        EventDTO createdEvent = eventService.createEvent(organizerId, createEventDTO);
        return new ResponseEntity<>(createdEvent, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDTO> getEventById(@PathVariable Long id) {
        EventDTO event = eventService.getEventById(id);
        return ResponseEntity.ok(event);
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<EventDTO>> getUpcomingEvents() {
        List<EventDTO> events = eventService.getUpcomingEvents();
        return ResponseEntity.ok(events);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<EventDTO>> getEventsByCategory(@PathVariable String category) {
        List<EventDTO> events = eventService.getEventsByCategory(category);
        return ResponseEntity.ok(events);
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<List<EventDTO>> getEventsByCity(@PathVariable String city) {
        List<EventDTO> events = eventService.getEventsByCity(city);
        return ResponseEntity.ok(events);
    }

    @GetMapping("/organizer/{organizerId}")
    public ResponseEntity<List<EventDTO>> getEventsByOrganizer(@PathVariable Long organizerId) {
        List<EventDTO> events = eventService.getEventsByOrganizer(organizerId);
        return ResponseEntity.ok(events);
    }

    @GetMapping("/search")
    public ResponseEntity<List<EventDTO>> searchEvents(@RequestParam String keyword) {
        List<EventDTO> events = eventService.searchEvents(keyword);
        return ResponseEntity.ok(events);
    }

    @GetMapping("/recommendations")
    public ResponseEntity<List<EventDTO>> getRecommendedEvents(@RequestParam Long userId) {
        List<EventDTO> recommendations = eventService.getRecommendedEvents(userId);
        return ResponseEntity.ok(recommendations);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventDTO> updateEvent(
            @PathVariable Long id,
            @Valid @RequestBody CreateEventDTO updateEventDTO) {
        EventDTO updatedEvent = eventService.updateEvent(id, updateEventDTO);
        return ResponseEntity.ok(updatedEvent);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateEventStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        eventService.updateEventStatus(id, status);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }
}
