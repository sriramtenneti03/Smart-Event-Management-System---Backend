# Smart Expense Tracker - Full Stack Web Application

A comprehensive expense tracking and financial insights platform built with Spring Boot (Backend) and Vanilla JavaScript (Frontend).

## Features

### Backend (Spring Boot)
- **User Authentication & Authorization** - JWT-based secure authentication
- **Expense Management** - Create, read, update, delete expenses
- **Category Management** - Organize expenses with custom categories
- **Shared Expenses** - Split expenses with friends/family
- **Financial Analytics** - Visual insights into spending patterns
- **RESTful API** - Complete API for frontend integration
- **OpenAPI Documentation** - Interactive API docs at `/swagger-ui.html`
- **H2 In-Memory Database** - Pre-loaded with sample data for testing
- **Caching** - Caffeine cache for improved performance
- **Rate Limiting** - Bucket4j for API protection
- **Monitoring** - Spring Actuator with Prometheus metrics

### Frontend (Vanilla JS)
- **Responsive Design** - Works on desktop and mobile
- **Dashboard** - Overview of expenses and statistics
- **Expense Tracking** - Full CRUD operations
- **Category Management** - Custom categories with icons and colors
- **Analytics Charts** - Visual representation using Chart.js
- **Single Page Application** - Smooth navigation without page reloads

## Quick Start

### Prerequisites
- Java 17+
- Maven 3.6+

### Running the Application

```bash
# Build and run
mvn clean spring-boot:run

# Or build JAR and run
mvn clean package
java -jar target/smart-expense-tracker-1.0.0.jar
```

### Access the Application

- **Frontend**: http://localhost:8080
- **API Documentation**: http://localhost:8080/swagger-ui.html
- **H2 Console**: http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:expensedb`
  - Username: `sa`
  - Password: (empty)

### Test Credentials

The application comes pre-loaded with test users:

| Email | Password |
|-------|----------|
| john@example.com | password123 |
| jane@example.com | password123 |

## API Endpoints

### Authentication
- `POST /api/auth/register` - Register new user
- `POST /api/auth/login` - Login user

### Expenses
- `GET /api/expenses` - Get all expenses
- `GET /api/expenses/{id}` - Get expense by ID
- `POST /api/expenses` - Create expense
- `PUT /api/expenses/{id}` - Update expense
- `DELETE /api/expenses/{id}` - Delete expense

### Categories
- `GET /api/categories` - Get all categories
- `GET /api/categories/{id}` - Get category by ID
- `POST /api/categories` - Create category
- `PUT /api/categories/{id}` - Update category
- `DELETE /api/categories/{id}` - Delete category

### Shared Expenses
- `GET /api/shared-expenses` - Get shared expenses
- `POST /api/shared-expenses` - Create shared expense

### Users
- `GET /api/users/profile` - Get current user profile
- `PUT /api/users/profile` - Update profile
- `DELETE /api/users/profile` - Delete account

## Project Structure

```
smart-expense-tracker/
├── src/main/java/com/expensetracker/
│   ├── config/          # Configuration classes
│   ├── controller/      # REST controllers
│   ├── dto/            # Data Transfer Objects
│   ├── entity/         # JPA Entities
│   ├── exception/      # Exception handling
│   ├── repository/     # Data repositories
│   ├── service/        # Business logic
│   └── util/           # Utility classes
├── src/main/resources/
│   ├── static/         # Frontend files
│   │   ├── css/        # Stylesheets
│   │   ├── js/         # JavaScript files
│   │   └── index.html  # Main HTML file
│   └── application.yml # Application configuration
└── pom.xml            # Maven dependencies
```

## Technologies Used

### Backend
- Spring Boot 3.2.0
- Spring Security + JWT
- Spring Data JPA
- H2 Database
- ModelMapper
- OpenAPI/Swagger
- Bucket4j (Rate Limiting)
- Caffeine (Caching)
- Spring Actuator

### Frontend
- HTML5
- CSS3
- Vanilla JavaScript (ES6+)
- Chart.js
 
## Configuration

Key configuration in `application.yml`:
- Database: H2 in-memory (for testing)
- JWT secret: Configurable via environment variable
- Server port: 8080
- CORS: Enabled for all origins

## Development

### Building for Production

```bash
mvn clean package -DskipTests
```

### Running Tests

```bash
mvn test
```

## License

MIT License
