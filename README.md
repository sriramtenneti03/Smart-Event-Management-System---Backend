# Smart Expense Tracker & Financial Insights Platform

A comprehensive, production-ready Java Spring Boot application for managing personal and shared expenses with advanced analytics and financial insights.

## Overview

This project solves the real problem of expense tracking and financial management for individuals and small teams. It provides a complete solution for:

- **Individual Expense Tracking**: Record, categorize, and manage personal expenses
- **Category Management**: Create custom expense categories with visual organization
- **Financial Analytics**: Monthly summaries, category-wise breakdowns, and spending trends
- **Shared Expenses**: Split bills and track shared expenses among friends/colleagues
- **User Authentication**: Secure JWT-based authentication system
- **RESTful API**: Complete REST API for integration with frontend applications

## Tech Stack

- **Framework**: Spring Boot 3.2.0
- **ORM**: Hibernate/JPA with Spring Data JPA
- **Database**: MySQL 8.0+ (PostgreSQL compatible)
- **Authentication**: JWT (JSON Web Tokens)
- **Build Tool**: Maven 3.8+
- **Testing**: JUnit 5 + Mockito
- **Code Quality**: Follows Spring Boot best practices and clean code principles

## Architecture

The project follows a layered architecture with clear separation of concerns:

```
src/main/java/com/expensetracker/
├── config/           # Spring Security & Application Configuration
├── controller/       # REST API Endpoints
├── service/          # Business Logic Layer
├── repository/       # Data Access Layer (Spring Data JPA)
├── entity/           # JPA Entity Models
├── dto/              # Data Transfer Objects
├── exception/        # Custom Exceptions & Global Exception Handler
└── util/             # Utility Classes (JWT Provider)
```

## Key Features

### 1. User Management
- User registration with email validation
- Secure password hashing using BCrypt
- JWT-based authentication
- User profile management

### 2. Expense Management
- Create, read, update, and delete expenses
- Categorize expenses with custom categories
- Attach tags and descriptions to expenses
- Recurring expense support (Daily, Weekly, Monthly, Yearly)
- Pagination and filtering support

### 3. Analytics & Reports
- Monthly spending summaries with statistics
- Category-wise expense breakdown with percentages
- Spending trends over multiple months
- Average, minimum, and maximum expense calculations
- Financial insights for budget planning

### 4. Shared Expenses
- Create shared expenses and split bills
- Track expense splits among participants
- Settlement tracking for shared expenses
- Bill splitting calculator

### 5. Security
- JWT-based authentication
- Password encryption with BCrypt
- CORS configuration for frontend integration
- Request validation with Spring Validation
- Global exception handling with meaningful error messages

## Database Schema

The application uses 5 main entities:

1. **Users**: Stores user account information
2. **Categories**: User-defined expense categories
3. **Expenses**: Individual expense records
4. **ExpenseSplits**: Participants in shared expenses
5. **SharedExpenses**: Group expenses and shared bills

All relationships are properly defined with foreign keys and cascade rules for data integrity.

## Setup & Installation

### Prerequisites

- Java 17 or higher
- Maven 3.8+
- MySQL 8.0+ or PostgreSQL 12+
- Git

### Step 1: Clone the Repository

```bash
git clone https://github.com/sriramtenneti03/Java-Backend.git
cd Java-Backend
```

### Step 2: Database Configuration

Create a MySQL database:

```sql
CREATE DATABASE expense_tracker;
```

### Step 3: Configure Application

Edit `src/main/resources/application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/expense_tracker?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
    username: root
    password: your_password
```

Alternatively, use environment variables:

```bash
export SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/expense_tracker
export SPRING_DATASOURCE_USERNAME=root
export SPRING_DATASOURCE_PASSWORD=your_password
```

### Step 4: Build the Project

```bash
mvn clean install
```

### Step 5: Run the Application

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

### Step 6: Verify Installation

```bash
curl http://localhost:8080/api/auth/health
```

Expected response:
```json
{
  "success": true,
  "message": "Service is healthy",
  "data": "OK",
  "timestamp": 1234567890
}
```

## API Endpoints

### Authentication Endpoints

```
POST   /api/auth/register          - Register new user
POST   /api/auth/login             - Login user
GET    /api/auth/health            - Health check
```

### User Endpoints

```
GET    /api/users/profile          - Get current user profile
GET    /api/users/{id}             - Get user by ID
PUT    /api/users/{id}             - Update user profile
```

### Category Endpoints

```
GET    /api/categories             - Get all user categories
POST   /api/categories             - Create new category
GET    /api/categories/{id}        - Get category by ID
PUT    /api/categories/{id}        - Update category
DELETE /api/categories/{id}        - Delete category
```

### Expense Endpoints

```
GET    /api/expenses               - Get all expenses (paginated)
POST   /api/expenses               - Create new expense
GET    /api/expenses/{id}          - Get expense by ID
PUT    /api/expenses/{id}          - Update expense
DELETE /api/expenses/{id}          - Delete expense
POST   /api/expenses/filter        - Get expenses with filters
GET    /api/expenses/summary/monthly/{yearMonth}  - Get monthly summary
GET    /api/expenses/analytics/category-breakdown - Get category breakdown
GET    /api/expenses/analytics/trends             - Get spending trends
```

### Shared Expense Endpoints

```
GET    /api/shared-expenses        - Get all shared expenses
POST   /api/shared-expenses        - Create shared expense
GET    /api/shared-expenses/{id}   - Get shared expense by ID
PUT    /api/shared-expenses/{id}/settle - Mark as settled
DELETE /api/shared-expenses/{id}   - Delete shared expense
GET    /api/shared-expenses/unsettled/list - Get unsettled expenses
```

## Example API Usage

### 1. Register a User

```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "john@example.com",
    "firstName": "John",
    "lastName": "Doe",
    "password": "Password123!"
  }'
```

### 2. Login

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "john@example.com",
    "password": "Password123!"
  }'
```

### 3. Create a Category

```bash
curl -X POST http://localhost:8080/api/categories \
  -H "Content-Type: application/json" \
  -H "Authorization: ******" \
  -d '{
    "name": "Food",
    "color": "#FF0000",
    "icon": "fork-knife"
  }'
```

### 4. Create an Expense

```bash
curl -X POST http://localhost:8080/api/expenses \
  -H "Content-Type: application/json" \
  -H "Authorization: ******" \
  -d '{
    "description": "Lunch at Restaurant",
    "amount": 25.50,
    "expenseDate": "2024-05-29",
    "categoryId": 1,
    "tags": "lunch, restaurant"
  }'
```

### 5. Get Monthly Summary

```bash
curl http://localhost:8080/api/expenses/summary/monthly/2024-05 \
  -H "Authorization: ******"
```

## Running Tests

### Run all tests

```bash
mvn test
```

### Run specific test class

```bash
mvn test -Dtest=UserServiceTest
```

### Run with coverage

```bash
mvn test jacoco:report
```

## Key Highlights for Resume

✅ **Production-Ready Architecture**: Properly layered architecture with separation of concerns

✅ **Advanced Spring Boot Features**:
   - Spring Security with JWT authentication
   - Spring Data JPA with custom queries
   - Global exception handling
   - Validation and error handling

✅ **Database Design**:
   - Relational model with proper normalization
   - Foreign key constraints and cascade operations
   - Indexed queries for performance
   - Flyway migrations for version control

✅ **Advanced Features**:
   - Analytics and reporting
   - Pagination and filtering
   - Recurring expenses
   - Bill splitting and shared expenses

✅ **Testing**:
   - Unit tests with Mockito
   - Integration tests with Spring Boot Test
   - Repository tests with H2 database

✅ **Code Quality**:
   - Clean code principles
   - SOLID principles
   - Comprehensive logging
   - Proper error messages

✅ **Documentation**:
   - Complete API documentation
   - Setup and installation guide
   - Example API usage
   - Architecture explanation

## Conclusion

This Smart Expense Tracker application demonstrates a complete, production-ready Spring Boot project that showcases expertise in:

- Java backend development
- Spring Boot framework
- Hibernate and JPA
- Database design and optimization
- REST API development
- User authentication and security
- Test-driven development
- Clean code practices

Perfect for showcasing your skills to potential employers!