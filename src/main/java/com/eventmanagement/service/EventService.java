package com.eventmanagement.service;

import com.eventmanagement.dto.CreateEventDTO;
import com.eventmanagement.dto.EventDTO;
import com.eventmanagement.entity.Event;
import com.eventmanagement.entity.User;
import com.eventmanagement.exception.ResourceNotFoundException;
import com.eventmanagement.repository.EventRepository;
import com.eventmanagement.repository.UserRepository;
import com.eventmanagement.repository.AttendeeRepository;
import com.eventmanagement.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service layer for Event operations and recommendations
 */
@Service
@RequiredArgsConstructor
@Transactional
public class EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final AttendeeRepository attendeeRepository;
    private final ReviewRepository reviewRepository;

    public EventDTO createEvent(Long organizerId, CreateEventDTO createEventDTO) {
        User organizer = userRepository.findById(organizerId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + organizerId));
        
        Event event = new Event();
        event.setTitle(createEventDTO.getTitle());
        event.setDescription(createEventDTO.getDescription());
        event.setCategory(createEventDTO.getCategory());
        event.setEventDate(createEventDTO.getEventDate());
        event.setLocation(createEventDTO.getLocation());
        event.setCity(createEventDTO.getCity());
        event.setCountry(createEventDTO.getCountry());
        event.setMaxAttendees(createEventDTO.getMaxAttendees());
        event.setImageUrl(createEventDTO.getImageUrl());
        event.setIsPublic(createEventDTO.getIsPublic());
        event.setOrganizer(organizer);
        event.setStatus("UPCOMING");
        
        Event savedEvent = eventRepository.save(event);
        return convertToDTO(savedEvent);
    }

    public EventDTO getEventById(Long id) {
        Event event = eventRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + id));
        return convertToDTO(event);
    }

    public List<EventDTO> getUpcomingEvents() {
        return eventRepository.findByIsPublicTrueOrderByEventDateDesc().stream()
            .filter(e -> e.getEventDate().isAfter(LocalDateTime.now()))
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    public List<EventDTO> getEventsByCategory(String category) {
        return eventRepository.findByCategoryOrderByEventDateDesc(category).stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    public List<EventDTO> getEventsByCity(String city) {
        return eventRepository.findEventsByCity(city).stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    public List<EventDTO> getEventsByOrganizer(Long organizerId) {
        return eventRepository.findByOrganizerIdOrderByEventDateDesc(organizerId).stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    public List<EventDTO> searchEvents(String keyword) {
        return eventRepository.searchEvents(keyword).stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    /**
     * Get personalized event recommendations based on user interests and location
     */
    public List<EventDTO> getRecommendedEvents(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        
        List<EventDTO> allEvents = getUpcomingEvents();
        
        return allEvents.stream()
            .filter(eventDTO -> {
                // Recommendation logic: events in same city
                if (user.getCity() != null && user.getCity().equals(eventDTO.getCity())) {
                    return true;
                }
                // Or events matching user interests
                if (user.getInterests() != null && eventDTO.getCategory() != null) {
                    String[] interests = user.getInterests().split(",");
                    for (String interest : interests) {
                        if (interest.trim().equalsIgnoreCase(eventDTO.getCategory())) {
                            return true;
                        }
                    }
                }
                return false;
            })
            .sorted((e1, e2) -> {
                // Sort by relevance and date
                Double rating1 = reviewRepository.getAverageRatingByEventId(e1.getId());
                Double rating2 = reviewRepository.getAverageRatingByEventId(e2.getId());
                if (rating1 == null) rating1 = 0.0;
                if (rating2 == null) rating2 = 0.0;
                return rating2.compareTo(rating1);
            })
            .collect(Collectors.toList());
    }

    public EventDTO updateEvent(Long eventId, CreateEventDTO updateEventDTO) {
        Event event = eventRepository.findById(eventId)
            .orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + eventId));
        
        event.setTitle(updateEventDTO.getTitle());
        event.setDescription(updateEventDTO.getDescription());
        event.setCategory(updateEventDTO.getCategory());
        event.setEventDate(updateEventDTO.getEventDate());
        event.setLocation(updateEventDTO.getLocation());
        event.setCity(updateEventDTO.getCity());
        event.setCountry(updateEventDTO.getCountry());
        event.setMaxAttendees(updateEventDTO.getMaxAttendees());
        event.setImageUrl(updateEventDTO.getImageUrl());
        
        Event updatedEvent = eventRepository.save(event);
        return convertToDTO(updatedEvent);
    }

    public void updateEventStatus(Long eventId, String status) {
        Event event = eventRepository.findById(eventId)
            .orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + eventId));
        event.setStatus(status);
        eventRepository.save(event);
    }

    public void deleteEvent(Long eventId) {
        Event event = eventRepository.findById(eventId)
            .orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + eventId));
        eventRepository.delete(event);
    }

    private EventDTO convertToDTO(Event event) {
        Integer attendeeCount = attendeeRepository.countInterestedAttendees(event.getId());
        
        return new EventDTO(
            event.getId(),
            event.getTitle(),
            event.getDescription(),
            event.getCategory(),
            event.getEventDate(),
            event.getLocation(),
            event.getCity(),
            event.getCountry(),
            event.getMaxAttendees(),
            null, // organizer DTO would be populated separately
            event.getStatus(),
            event.getImageUrl(),
            event.getIsPublic(),
            event.getCreatedAt(),
            event.getUpdatedAt(),
            attendeeCount
        );
    }
}
