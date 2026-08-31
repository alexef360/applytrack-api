# ApplyTrack API

REST API for tracking job and internship applications (Praktikum, Werkstudent, Junior roles).

Spring Boot project with a layered architecture (controller, service, repository), request validation, centralized error handling, automated tests, OpenAPI documentation, and optional PostgreSQL support via Docker.

## Tech stack

- Java 25, Spring Boot 4
- Spring Web, Spring Data JPA, Validation
- H2 (default) · PostgreSQL 16 (Docker)
- springdoc-openapi (Swagger UI)
- JUnit 5, Mockito, MockMvc

## Getting started

**Requirements:** JDK 25, Maven (or IntelliJ). Docker is required only for the PostgreSQL setup.

### H2 (default profile)

```bash
mvn spring-boot:run
```

Application URL: `http://localhost:8080`

H2 console: `http://localhost:8080/h2-console` — JDBC URL: `jdbc:h2:mem:applytrack`

The default profile uses an in-memory database. Data does not persist between application restarts.

### PostgreSQL (Docker)

Start the database:

```bash
docker compose up -d
```

Run the application with the `postgres` profile:

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=postgres
```

In IntelliJ, set **Active profiles** to `postgres` in the run configuration.

Data is stored in the Docker volume `applytrack-data` and persists across application restarts. To reset the database, run `docker compose down -v`.

Stop the database:

```bash
docker compose down
```

## API documentation

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

## Endpoints

| Method | URL | Description |
|--------|-----|-------------|
| GET | `/api/applications` | List all applications |
| GET | `/api/applications?status=APPLIED` | Filter by status |
| GET | `/api/applications/{id}` | Get application by ID |
| POST | `/api/applications` | Create application (201) |
| PUT | `/api/applications/{id}` | Update application |
| DELETE | `/api/applications/{id}` | Delete application (204) |
| GET | `/api/applications/stats` | Application count per status |

**Application statuses:** `SAVED`, `APPLIED`, `INTERVIEW`, `OFFER`, `REJECTED`, `GHOSTED`

Validation errors (400) and missing resources (404) are returned as structured JSON via `@RestControllerAdvice`.

### Request example

```http
POST /api/applications
Content-Type: application/json

{
  "company": "Sopra Steria",
  "role": "Werkstudent Java",
  "status": "APPLIED",
  "appliedAt": "2026-08-20",
  "jobUrl": "https://example.com/job",
  "notes": "Applied via LinkedIn"
}
```

### Error response example (404)

```json
{
  "status": 404,
  "message": "Application not found with id: 99",
  "path": "/api/applications/99"
}
```

## Project structure

```
controller/    REST layer
service/       Business logic
repository/    Spring Data JPA
model/         Entities and enums
dto/           API response objects
exceptions/    Custom exceptions
config/        OpenAPI configuration
```

## Tests

```bash
mvn test
```

Tests use the H2 profile and do not require Docker.

- `ApplicationServiceTest` — service layer (Mockito)
- `ApplicationControllerTest` — validation and error responses (MockMvc)

## Author

Aleksandra Frej — [github.com/alexef360](https://github.com/alexef360)
