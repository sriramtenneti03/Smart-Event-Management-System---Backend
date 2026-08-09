# API Documentation - Smart Event Management System

## Base URL
```
http://localhost:8080/api
```

## Content Type
All requests and responses use `application/json`

---

## 1. USER ENDPOINTS

### 1.1 Register a New User
**POST** `/users/register`

**Request Body:**
```json
{
  "email": "user@example.com",
  "fullName": "John Doe",
  "password": "securePassword123",
  "phoneNumber": "555-0123",
  "city": "New York",
  "country": "USA",
  "interests": "Technology,Music,Networking"
}
```

**Response (201 Created):**
```json
{
  "id": 1,
  "email": "user@example.com",
  "fullName": "John Doe",
  "phoneNumber": "555-0123",
  "bio": null,
  "profileImageUrl": null,
  "city": "New York",
  "country": "USA",
  "interests": "Technology,Music,Networking",
  "createdAt": "2024-05-29T09:15:19.201Z",
  "updatedAt": "2024-05-29T09:15:19.201Z",
  "isActive": true
}
```

### 1.2 Get User by ID
**GET** `/users/{id}`

**Response (200 OK):**
```json
{
  "id": 1,
  "email": "user@example.com",
  "fullName": "John Doe",
  ...
}
```

### 1.3 Get User by Email
**GET** `/users/email/{email}`

**Response (200 OK):**
Same as GET /users/{id}

### 1.4 Get All Active Users
**GET** `/users`

**Response (200 OK):**
```json
[
  {
    "id": 1,
    "email": "user@example.com",
    ...
  },
  ...
]
```

### 1.5 Search Users
**GET** `/users/search?keyword={keyword}`

**Response (200 OK):**
Array of matching users

### 1.6 Get Users by City
**GET** `/users/city/{city}`

**Response (200 OK):**
Array of users from specified city

### 1.7 Update User Profile
**PUT** `/users/{id}`

**Request Body:**
```json
{
  "fullName": "Jane Doe",
  "phoneNumber": "555-0124",
  "city": "San Francisco",
  "country": "USA",
  "interests": "Technology,Startup"
}
```

**Response (200 OK):**
Updated user object

### 1.8 Delete User
**DELETE** `/users/{id}`

**Response (204 No Content)**

---

## 2. EVENT ENDPOINTS

### 2.1 Create Event
**POST** `/events?organizerId={organizerId}`

**Request Body:**
```json
{
  "title": "Java Spring Boot Masterclass",
  "description": "Learn Spring Boot best practices",
  "category": "Technology",
  "eventDate": "2024-06-15T18:00:00",
  "location": "Tech Hub, Downtown",
  "city": "New York",
  "country": "USA",
  "maxAttendees": 100,
  "imageUrl": "https://example.com/image.jpg",
  "isPublic": true
}
```

**Response (201 Created):**
```json
{
  "id": 1,
  "title": "Java Spring Boot Masterclass",
  "description": "Learn Spring Boot best practices",
  "category": "Technology",
  "eventDate": "2024-06-15T18:00:00",
  "location": "Tech Hub, Downtown",
  "city": "New York",
  "country": "USA",
  "maxAttendees": 100,
  "organizer": {
    "id": 1,
    "email": "organizer@example.com",
    ...
  },
  "status": "UPCOMING",
  "imageUrl": "https://example.com/image.jpg",
  "isPublic": true,
  "createdAt": "2024-05-29T09:15:19.201Z",
  "updatedAt": "2024-05-29T09:15:19.201Z",
  "attendeeCount": 0
}
```

### 2.2 Get Event by ID
**GET** `/events/{id}`

**Response (200 OK):**
Event object (see 2.1 for structure)

### 2.3 Get Upcoming Events
**GET** `/events/upcoming`

**Response (200 OK):**
Array of upcoming public events

### 2.4 Get Events by Category
**GET** `/events/category/{category}`

**Response (200 OK):**
Array of events in category

### 2.5 Get Events by City
**GET** `/events/city/{city}`

**Response (200 OK):**
Array of events in city

### 2.6 Get Events by Organizer
**GET** `/events/organizer/{organizerId}`

**Response (200 OK):**
Array of events organized by user

### 2.7 Search Events
**GET** `/events/search?keyword={keyword}`

**Response (200 OK):**
Array of matching events

### 2.8 Get Recommended Events
**GET** `/events/recommendations?userId={userId}`

**Response (200 OK):**
Personalized event recommendations based on user interests and location

### 2.9 Update Event
**PUT** `/events/{id}`

**Request Body:**
Same as 2.1 (any fields can be updated)

**Response (200 OK):**
Updated event object

### 2.10 Update Event Status
**PATCH** `/events/{id}/status?status={status}`

Query Parameters:
- `status`: UPCOMING, ONGOING, COMPLETED, CANCELLED

**Response (204 No Content)**

### 2.11 Delete Event
**DELETE** `/events/{id}`

**Response (204 No Content)**

---

## 3. ATTENDEE/RSVP ENDPOINTS

### 3.1 RSVP to Event
**POST** `/attendees/rsvp?eventId={eventId}&userId={userId}&status={status}&numberOfGuests={numberOfGuests}`

Query Parameters:
- `eventId`: ID of the event
- `userId`: ID of the user
- `status`: ATTENDING, INTERESTED, NOT_ATTENDING, MAYBE
- `numberOfGuests` (optional): Number of additional guests (default: 0)

**Response (201 Created)**

### 3.2 Cancel RSVP
**DELETE** `/attendees/cancel-rsvp?eventId={eventId}&userId={userId}`

**Response (204 No Content)**

### 3.3 Get Event Attendees
**GET** `/attendees/event/{eventId}`

**Response (200 OK):**
```json
[
  {
    "id": 1,
    "event": { ... },
    "user": { ... },
    "status": "ATTENDING",
    "numberOfGuests": 1,
    "isCheckedIn": false,
    "checkInTime": null,
    "rsvpDate": "2024-05-29T09:15:19.201Z",
    "notes": null
  },
  ...
]
```

### 3.4 Get Attendees by Status
**GET** `/attendees/event/{eventId}/status/{status}`

Path Parameters:
- `status`: ATTENDING, INTERESTED, NOT_ATTENDING, MAYBE

**Response (200 OK):**
Array of attendees with specified status

### 3.5 Get User's Attending Events
**GET** `/attendees/user/{userId}`

**Response (200 OK):**
Array of events user is attending

### 3.6 Get Attendee Count
**GET** `/attendees/count/{eventId}`

**Response (200 OK):**
```json
45
```

### 3.7 Check-in Attendee
**POST** `/attendees/{attendeeId}/check-in`

**Response (204 No Content)**

---

## 4. REVIEW ENDPOINTS

### 4.1 Create Review
**POST** `/reviews?eventId={eventId}&reviewerId={reviewerId}&rating={rating}&comment={comment}`

Query Parameters:
- `eventId`: ID of the event
- `reviewerId`: ID of the reviewer
- `rating`: 1-5
- `comment` (optional): Review text

**Response (201 Created):**
```json
{
  "id": 1,
  "event": { ... },
  "reviewer": { ... },
  "rating": 5,
  "comment": "Great event!",
  "createdAt": "2024-05-29T09:15:19.201Z",
  "helpfulCount": 0
}
```

### 4.2 Get Event Reviews
**GET** `/reviews/event/{eventId}`

**Response (200 OK):**
Array of reviews for event

### 4.3 Get User Reviews
**GET** `/reviews/user/{userId}`

**Response (200 OK):**
Array of reviews written by user

### 4.4 Get Event Average Rating
**GET** `/reviews/event/{eventId}/average-rating`

**Response (200 OK):**
```json
4.5
```

### 4.5 Get Review Count
**GET** `/reviews/event/{eventId}/count`

**Response (200 OK):**
```json
12
```

### 4.6 Update Review
**PUT** `/reviews/{reviewId}?rating={rating}&comment={comment}`

Query Parameters:
- `rating`: 1-5
- `comment` (optional): Updated review text

**Response (204 No Content)**

### 4.7 Delete Review
**DELETE** `/reviews/{reviewId}`

**Response (204 No Content)**

### 4.8 Mark Review as Helpful
**POST** `/reviews/{reviewId}/mark-helpful`

**Response (204 No Content)**

---

## Error Responses

All error responses follow this format:

```json
{
  "timestamp": "2024-05-29T09:15:19.201Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation error message or custom error"
}
```

### Common HTTP Status Codes
- `200 OK` - Success
- `201 Created` - Resource created
- `204 No Content` - Success with no response body
- `400 Bad Request` - Validation error
- `404 Not Found` - Resource not found
- `500 Internal Server Error` - Server error

---

## Data Types

### Event Status
- UPCOMING
- ONGOING
- COMPLETED
- CANCELLED

### RSVP Status
- ATTENDING
- INTERESTED
- NOT_ATTENDING
- MAYBE

### Rating
- Integer from 1 to 5

---

## Examples

### Complete Event Creation Flow

1. Register user:
```bash
curl -X POST http://localhost:8080/api/users/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "organizer@example.com",
    "fullName": "Event Organizer",
    "password": "password123",
    "city": "New York",
    "interests": "Technology"
  }'
```

2. Create event:
```bash
curl -X POST "http://localhost:8080/api/events?organizerId=1" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Tech Meetup",
    "description": "Network with tech professionals",
    "category": "Technology",
    "eventDate": "2024-06-15T18:00:00",
    "location": "Tech Hub",
    "city": "New York",
    "maxAttendees": 50,
    "isPublic": true
  }'
```

3. Register attendee and RSVP:
```bash
curl -X POST "http://localhost:8080/api/users/register" \
  -H "Content-Type: application/json" \
  -d '{
    "email": "attendee@example.com",
    "fullName": "Attendee Name",
    "password": "password123",
    "city": "New York"
  }'

curl -X POST "http://localhost:8080/api/attendees/rsvp?eventId=1&userId=2&status=ATTENDING&numberOfGuests=1"
```

4. Leave a review:
```bash
curl -X POST "http://localhost:8080/api/reviews?eventId=1&reviewerId=2&rating=5&comment=Great+event!" \
  -H "Content-Type: application/json"
```
