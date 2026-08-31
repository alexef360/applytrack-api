# ApplyTrack API

REST API to track job and internship applications (Praktikum / Werkstudent / Junior roles).

Solo Spring Boot project — layered architecture (controller → service → repository), validation, global error handling, automated tests, Swagger docs, and PostgreSQL via Docker.

## Tech stack

- Java 25
- Spring Boot 4
- Spring Web, Spring Data JPA, Validation
- H2 (in-memory) — default profile, used for quick dev and tests
- PostgreSQL 16 — optional profile with Docker Compose
- springdoc-openapi (Swagger UI)
- Docker, Docker Compose
- JUnit 5, Mockito, MockMvc

## Features

- Full CRUD for applications (`/api/applications`)
- Filter by status: `GET /api/applications?status=INTERVIEW`
- Stats: `GET /api/applications/stats` (total + count per status)
- Validation (`@NotBlank` on company/role)
- Consistent JSON errors for 404 / 400 via `@RestControllerAdvice`
- Interactive API docs (Swagger UI)
- Unit tests (service) + MockMvc test (validation → 400)
- PostgreSQL with persistent data via Docker volume

## Application statuses

`SAVED`, `APPLIED`, `INTERVIEW`, `OFFER`, `REJECTED`, `GHOSTED`

## Run

Prerequisites: JDK 25, Maven (or IntelliJ).

### Option A — H2 (default, no Docker)

```bash
mvn spring-boot:run
```

Or run `ApplytrackApiApplication` from IntelliJ (no active profile).

API base: `http://localhost:8080`

H2 console (optional): `http://localhost:8080/h2-console`  
(JDBC URL: `jdbc:h2:mem:applytrack`)

> H2 is in-memory — data is lost after restart.

### Option B — PostgreSQL (Docker)

1. Start the database:

```bash
docker compose up -d
```

2. Run the app with the `postgres` profile:

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=postgres
```

Or in IntelliJ: Run Configuration → **Active profiles:** `postgres`

> Data persists across app restarts (stored in Docker volume `applytrack-data`).  
> Keep Docker Desktop running. Do not use `docker compose down -v` unless you want to wipe data.

Stop the database:

```bash
docker compose down
```

## API documentation

Swagger UI: `http://localhost:8080/swagger-ui.html`  
OpenAPI JSON: `http://localhost:8080/v3/api-docs`

## Endpoints

| Method | URL | Description |
|--------|-----|-------------|
| GET | `/api/applications` | List all |
| GET | `/api/applications?status=APPLIED` | Filter by status |
| GET | `/api/applications/{id}` | Get one |
| POST | `/api/applications` | Create (201) |
| PUT | `/api/applications/{id}` | Update |
| DELETE | `/api/applications/{id}` | Delete (204) |
| GET | `/api/applications/stats` | Totals per status |

### Example: create

```http
POST http://localhost:8080/api/applications
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

### Example: error (404)

```json
{
  "status": 404,
  "message": "Application not found with id: 99",
  "path": "/api/applications/99"
}
```

## Project structure

```
controller/   → HTTP mapping, ResponseEntity
service/      → business logic (CRUD, stats)
repository/   → Spring Data JPA
model/        → entities & enums
dto/          → ErrorResponse, ApplicationStatsResponse
exceptions/   → ApplicationNotFoundException
config/       → OpenAPI configuration
```

## Tests

```bash
mvn test
```

Tests run on H2 (default profile) — no Docker required.

- `ApplicationServiceTest` — stats zeros, not-found exception (Mockito)
- `ApplicationControllerTest` — blank company → 400 (MockMvc)

## Author

Aleksandra Frej — [github.com/alexef360](https://github.com/alexef360)
