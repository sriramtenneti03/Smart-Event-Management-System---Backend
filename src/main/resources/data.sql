-- Sample data for Event Management System
-- Run this after the application has created the schema

-- Insert Sample Users
INSERT INTO users (email, full_name, password, phone_number, bio, city, country, interests, is_active, created_at, updated_at) VALUES
('john.doe@example.com', 'John Doe', 'hashed_password_123', '555-0101', 'Tech enthusiast and event organizer', 'New York', 'USA', 'Technology,Networking,Music', true, NOW(), NOW()),
('jane.smith@example.com', 'Jane Smith', 'hashed_password_456', '555-0102', 'Love attending tech conferences', 'San Francisco', 'USA', 'Technology,Business,Startup', true, NOW(), NOW()),
('mike.johnson@example.com', 'Mike Johnson', 'hashed_password_789', '555-0103', 'Music lover and event attendee', 'Los Angeles', 'USA', 'Music,Entertainment,Art', true, NOW(), NOW()),
('sarah.williams@example.com', 'Sarah Williams', 'hashed_password_012', '555-0104', 'Food and travel enthusiast', 'Seattle', 'USA', 'Food,Travel,Networking', true, NOW(), NOW()),
('alex.brown@example.com', 'Alex Brown', 'hashed_password_345', '555-0105', 'Sports and fitness enthusiast', 'Boston', 'USA', 'Sports,Fitness,Technology', true, NOW(), NOW());

-- Insert Sample Events
INSERT INTO events (title, description, category, event_date, location, city, country, max_attendees, organizer_id, status, is_public, created_at, updated_at) VALUES
('Java Spring Boot Masterclass', 'Learn Spring Boot best practices and build scalable applications', 'Technology', '2024-06-15 18:00:00', 'Tech Hub, Manhattan', 'New York', 'USA', 100, 1, 'UPCOMING', true, NOW(), NOW()),
('JavaScript & React Workshop', 'Comprehensive guide to modern JavaScript and React development', 'Technology', '2024-06-20 14:00:00', 'Innovation Center', 'San Francisco', 'USA', 80, 2, 'UPCOMING', true, NOW(), NOW()),
('Annual Tech Conference 2024', 'The biggest tech conference of the year featuring industry leaders', 'Technology', '2024-07-10 09:00:00', 'Convention Center', 'San Francisco', 'USA', 500, 2, 'UPCOMING', true, NOW(), NOW()),
('Live Music Night', 'Enjoy live performances from local artists and bands', 'Music', '2024-06-25 20:00:00', 'The Blue Note', 'Los Angeles', 'USA', 150, 3, 'UPCOMING', true, NOW(), NOW()),
('Foodie Meetup', 'Connect with food enthusiasts and explore local cuisines', 'Food', '2024-06-30 18:00:00', 'Downtown Market', 'Seattle', 'USA', 50, 4, 'UPCOMING', true, NOW(), NOW()),
('Network & Coffee', 'Morning networking session with professionals from various industries', 'Networking', '2024-06-12 09:00:00', 'Coffee House, Downtown', 'New York', 'USA', 30, 1, 'UPCOMING', true, NOW(), NOW()),
('Fitness Boot Camp', 'High-intensity workout session with certified trainers', 'Sports', '2024-06-18 06:00:00', 'Central Park', 'New York', 'USA', 40, 5, 'UPCOMING', true, NOW(), NOW());

-- Insert Sample Attendees (RSVPs)
INSERT INTO attendees (event_id, user_id, status, number_of_guests, is_checked_in, rsvp_date) VALUES
(1, 2, 'ATTENDING', 1, false, NOW()),
(1, 3, 'INTERESTED', 0, false, NOW()),
(1, 4, 'ATTENDING', 2, false, NOW()),
(2, 1, 'ATTENDING', 1, false, NOW()),
(2, 5, 'MAYBE', 1, false, NOW()),
(3, 1, 'INTERESTED', 0, false, NOW()),
(3, 4, 'ATTENDING', 1, false, NOW()),
(4, 3, 'ATTENDING', 3, false, NOW()),
(5, 4, 'ATTENDING', 1, false, NOW()),
(6, 1, 'ATTENDING', 1, false, NOW()),
(6, 2, 'INTERESTED', 0, false, NOW()),
(7, 5, 'ATTENDING', 0, false, NOW());

-- Insert Sample Reviews
INSERT INTO reviews (event_id, reviewer_id, rating, comment, helpful_count, created_at) VALUES
(1, 2, 5, 'Excellent workshop! Learned so much about Spring Boot.', 3, NOW()),
(1, 4, 4, 'Great content, wish it was a bit longer.', 1, NOW()),
(2, 1, 5, 'Best React workshop I have attended!', 5, NOW()),
(4, 3, 5, 'Amazing performances and great atmosphere!', 2, NOW()),
(5, 4, 4, 'Good variety of food options, will attend again.', 0, NOW());
