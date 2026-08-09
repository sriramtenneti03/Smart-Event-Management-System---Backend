package com.eventmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * DTO for Event entity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventDTO {
    private Long id;
    private String title;
    private String description;
    private String category;
    private LocalDateTime eventDate;
    private String location;
    private String city;
    private String country;
    private Integer maxAttendees;
    private UserDTO organizer;
    private String status;
    private String imageUrl;
    private Boolean isPublic;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer attendeeCount;
}
