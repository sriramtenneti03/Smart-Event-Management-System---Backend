# System Architecture - Smart Event Management System

## Overview

The Smart Event Management System is a Spring Boot backend application that follows a layered architecture pattern. This document provides an overview of the system design and key architectural decisions.

## Layered Architecture

```
┌─────────────────────────────────────────┐
│      REST API Controllers               │  (Presentation Layer)
│  (UserController, EventController, ...) │
└──────────────────┬──────────────────────┘
                   │
                   ▼
┌─────────────────────────────────────────┐
│      Business Logic Services            │  (Service Layer)
│  (UserService, EventService, ...)       │
└──────────────────┬──────────────────────┘
                   │
                   ▼
┌─────────────────────────────────────────┐
│   Data Access Layer (Repositories)      │  (Data Layer)
│  (UserRepository, EventRepository, ...) │
└──────────────────┬──────────────────────┘
                   │
                   ▼
┌─────────────────────────────────────────┐
│      Database (MySQL)                   │  (Persistence)
└─────────────────────────────────────────┘
```

## Component Details

### 1. Presentation Layer (Controllers)

**Responsibility**: Handle HTTP requests and responses

**Components**:
- `UserController` - User registration and profile management
- `EventController` - Event CRUD operations
- `AttendeeController` - RSVP and check-in management
- `ReviewController` - Event reviews and ratings

**Characteristics**:
- Validates incoming requests using `@Valid` annotations
- Converts between request/response formats
- Returns appropriate HTTP status codes
- Delegates business logic to services

### 2. Service Layer

**Responsibility**: Implement business logic and orchestrate operations

**Components**:
- `UserService` - User management business logic
- `EventService` - Event management and recommendations
- `AttendeeService` - RSVP and attendee management
- `ReviewService` - Review and rating management

**Key Features**:
- `@Transactional` for database operations
- Complex business logic (e.g., event recommendations)
- Input validation
- Data transformation between entities and DTOs

**Example: Event Recommendations Logic**
```
1. Fetch all upcoming public events
2. Filter by user's location
3. Filter by user's interests
4. Sort by event ratings
5. Return top matches
```

### 3. Data Access Layer (Repositories)

**Responsibility**: Handle database operations

**Components**:
- `UserRepository` - User data access
- `EventRepository` - Event data access
- `AttendeeRepository` - Attendee data access
- `ReviewRepository` - Review data access

**Technology**: Spring Data JPA with custom queries

**Query Types**:
- CRUD operations (inherited from JpaRepository)
- Named methods for common queries
- `@Query` annotations for complex queries

### 4. Entities (Domain Models)

**Responsibility**: Represent database tables and business concepts

**Components**:
- `User` - User profile and information
- `Event` - Event details
- `Attendee` - RSVP information
- `Review` - Event reviews and ratings

**Key Features**:
- Hibernate ORM annotations
- Relationships (One-to-Many, Many-to-One)
- Lifecycle callbacks (`@PrePersist`, `@PreUpdate`)
- Timestamp management

### 5. DTOs (Data Transfer Objects)

**Responsibility**: Transfer data between layers without exposing entities

**Components**:
- `UserDTO` - User data for API responses
- `CreateUserDTO` - User registration input
- `EventDTO` - Event data for API responses
- `CreateEventDTO` - Event creation input

**Benefits**:
- Security (hide sensitive fields)
- Flexibility (API structure independent of database)
- Validation (request-specific rules)

### 6. Exception Handling

**Components**:
- `ResourceNotFoundException` - Custom exception for missing resources
- `GlobalExceptionHandler` - Centralized exception handling

**Features**:
- Consistent error response format
- Proper HTTP status codes
- Validation error details

## Database Design

### Entity Relationships

```
User
├─ One-to-Many → Events (as organizer)
├─ One-to-Many → Attendees
└─ One-to-Many → Reviews

Event
├─ Many-to-One ← User (organizer)
├─ One-to-Many → Attendees
└─ One-to-Many → Reviews

Attendee
├─ Many-to-One ← Event
└─ Many-to-One ← User

Review
├─ Many-to-One ← Event
└─ Many-to-One ← User (reviewer)
```

### Table Structure

**users**
- Primary key: id
- Unique constraint: email
- Soft delete: is_active flag

**events**
- Primary key: id
- Foreign key: organizer_id → users(id)
- Status: UPCOMING, ONGOING, COMPLETED, CANCELLED

**attendees**
- Primary key: id
- Composite unique constraint: (event_id, user_id)
- Status: ATTENDING, INTERESTED, NOT_ATTENDING, MAYBE
- Check-in tracking

**reviews**
- Primary key: id
- Foreign keys: event_id, reviewer_id
- Rating: 1-5 stars

## Key Design Patterns

### 1. Dependency Injection
- All dependencies injected via constructor (`@RequiredArgsConstructor`)
- Improves testability and loose coupling

### 2. DTO Pattern
- Separate request/response models from entities
- Flexible API evolution

### 3. Repository Pattern
- Abstraction over data access logic
- Spring Data JPA handles CRUD operations

### 4. Service Locator / Facade
- Services provide unified business operations
- Hide complexity from controllers

### 5. Soft Delete
- Users marked inactive instead of deleted
- Preserves data integrity and audit trail

### 6. Transaction Management
- Service layer methods marked `@Transactional`
- Ensures ACID properties for database operations

## Recommendations Algorithm

The system provides intelligent event recommendations based on:

1. **Location-Based Filtering**
   - Events in user's city have highest priority
   - Reduces travel distance

2. **Interest-Based Filtering**
   - Match event category with user interests
   - Comma-separated interests in user profile

3. **Quality Sorting**
   - Events sorted by average rating
   - Popular, well-reviewed events appear first

4. **Availability Filtering**
   - Only upcoming events returned
   - Excludes completed or cancelled events

**Algorithm Pseudo-code**:
```
recommendedEvents = []
for each upcoming public event:
    if event.city == user.city OR 
       event.category in user.interests:
        add to recommendedEvents

sort recommendedEvents by average rating DESC
return recommendedEvents
```

## Scalability Considerations

### Database Optimization
- Proper indexing on frequently queried columns
- Connection pooling (HikariCP)
- Batch operations for bulk inserts

### API Design
- Pagination support ready (can be added)
- Query optimization with proper joins
- Caching opportunities identified

### Future Enhancements
- Cache layer (Redis) for recommendations
- Elasticsearch for full-text search
- Message queue for async operations
- Event-driven architecture with Kafka

## Security Considerations

**Current Implementation**:
- Input validation using Bean Validation
- SQL injection prevention via parameterized queries (JPA)
- Exception handling prevents information leakage

**Recommendations for Production**:
- JWT authentication
- Role-based access control (RBAC)
- Password hashing (BCrypt)
- API rate limiting
- HTTPS/TLS encryption
- Input sanitization
- SQL injection prevention (already handled by JPA)

## Testing Strategy

### Unit Tests
- Service layer business logic
- Repository query correctness

### Integration Tests
- Full request/response flow
- Database operations

### Test Database
- H2 in-memory database for testing
- Isolation between test cases
- Quick test execution

## Deployment Architecture

```
Client (Web Browser / Mobile App)
         ↓
   API Gateway (Optional)
         ↓
   Load Balancer (Optional)
         ↓
Spring Boot Application (Container)
         ↓
   Database Connection Pool
         ↓
   MySQL Database
```

## Configuration Management

**Properties by Environment**:
- `application.yml` - Production configuration
- `application-dev.properties` - Development overrides
- `application-test.properties` - Test configuration

**Externalized Configuration**:
- Database credentials (environment variables)
- Server port
- Logging levels
- Hibernate settings

## Monitoring & Logging

**Logging Framework**: SLF4J with Logback

**Levels**:
- `ERROR` - Application errors
- `WARN` - Warnings
- `INFO` - Important events
- `DEBUG` - Detailed information (development)

**Endpoints**:
- `/actuator/health` - Application health status
- `/actuator/info` - Application information

## File Structure

```
src/
├── main/
│   ├── java/com/eventmanagement/
│   │   ├── controller/        # REST endpoints
│   │   ├── service/           # Business logic
│   │   ├── repository/        # Data access
│   │   ├── entity/            # Domain models
│   │   ├── dto/               # Data transfer objects
│   │   ├── exception/         # Custom exceptions
│   │   └── EventManagementApplication.java
│   └── resources/
│       ├── application.yml    # Configuration
│       └── data.sql           # Sample data
└── test/
    └── java/com/eventmanagement/  # Unit tests
```

## Conclusion

The Smart Event Management System demonstrates professional-grade backend architecture suitable for production applications. The layered design promotes maintainability, testability, and scalability while following Spring Boot best practices.
