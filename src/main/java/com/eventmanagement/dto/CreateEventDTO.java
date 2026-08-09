package com.eventmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Future;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * DTO for creating a new event
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateEventDTO {
    
    @NotBlank(message = "Event title is required")
    private String title;

    @NotBlank(message = "Event description is required")
    private String description;

    @NotBlank(message = "Event category is required")
    private String category;

    @NotNull(message = "Event date is required")
    @Future(message = "Event date must be in the future")
    private LocalDateTime eventDate;

    @NotBlank(message = "Event location is required")
    private String location;

    private String city;
    private String country;
    
    private Integer maxAttendees;
    private String imageUrl;
    private Boolean isPublic = true;
}
