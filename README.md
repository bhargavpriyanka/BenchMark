# BenchMark — Workout Logger REST API

A RESTful workout logging API built with Java 21 and Spring Boot 3, enabling users to track gym sessions, log exercises with sets, reps, and weight, and monitor strength progress over time.

## Tech Stack

- **Backend** — Java 21, Spring Boot 3
- **Security** — Spring Security, JWT
- **Database** — PostgreSQL, Spring Data JPA
- **Testing** — JUnit 5, Mockito
- **DevOps** — Docker, Docker Compose
- **API Tool** — Postman

## Features

- JWT authentication with custom filter chain and BCrypt password hashing
- Stateless session management
- Category and muscle group management
- Exercise creation with many-to-many muscle relationships
- Workout session logging per authenticated user
- Workout exercise logging with sets, reps, and weight
- Global exception handling with custom exceptions
- Input validation with meaningful error responses
- 100% service layer method and line coverage

## Architecture

Follows a layered architecture pattern: Controller → Service → Repository

- **Controller** — handles HTTP requests and responses
- **Service** — contains business logic
- **Repository** — handles database access via Spring Data JPA

## Data Model

Six entities:

- `User` has many `WorkoutSession`
- `WorkoutSession` has many `WorkoutExercise`
- `WorkoutExercise` references one `Exercise`
- `Exercise` belongs to one `Category`
- `Exercise` targets many `Muscles` (many-to-many)

## Setup

1. Clone the repository
2. Copy `.env.example` to `.env` and fill in your values
3. Copy `application-example.properties` to `src/main/resources/application.properties` and fill in your values
4. Make sure Docker Desktop is running
5. Run `mvn clean package -DskipTests`
6. Run `docker-compose up --build`

## Running the App

Make sure Docker Desktop is running, then:

```bash
mvn clean package -DskipTests
docker-compose up --build
```

App runs on `http://localhost:8080`

## API Endpoints

### Auth
| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| POST | /api/user | Register | No |
| POST | /api/auth/login | Login | No |

### Categories
| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| POST | /api/categories | Create category | Yes |
| GET | /api/categories | Get all categories | Yes |
| GET | /api/categories/{id} | Get category by id | Yes |

### Muscles
| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| POST | /api/muscles | Create muscle | Yes |
| GET | /api/muscles | Get all muscles | Yes |
| GET | /api/muscles/{id} | Get muscle by id | Yes |

### Exercises
| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| POST | /api/exercises | Create exercise | Yes |
| GET | /api/exercises | Get all exercises | Yes |
| GET | /api/exercises/{id} | Get exercise by id | Yes |

### Workout Sessions
| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| POST | /api/workout-session | Create session | Yes |
| GET | /api/workout-session | Get user's sessions | Yes |
| GET | /api/workout-session/{id} | Get session by id | Yes |

### Workout Exercises
| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| POST | /api/workout-exercise | Log workout exercise | Yes |
| GET | /api/workout-exercise | Get all | Yes |
| GET | /api/workout-exercise/{id} | Get by id | Yes |

## Testing

Unit tests written with JUnit 5 and Mockito covering all service classes.

- 100% method coverage
- 100% line coverage
- Tests cover happy paths, edge cases, and exception scenarios

Run tests:

```bash
mvn test
```

## Security

- Passwords hashed with BCrypt before storing
- JWT token required for all endpoints except registration and login
- Token extracted from `Authorization: Bearer <token>` header
- Authenticated user resolved from JWT token in service layer