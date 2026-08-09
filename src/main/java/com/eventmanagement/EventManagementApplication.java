package com.eventmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the Smart Event Management System
 * 
 * This application provides a comprehensive backend service for:
 * - Creating and managing events
 * - Managing event attendees and RSVPs
 * - Providing intelligent event recommendations
 * - Handling user profiles and preferences
 */
@SpringBootApplication
public class EventManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventManagementApplication.class, args);
    }

}
