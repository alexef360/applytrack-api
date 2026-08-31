# ApplyTrack API

REST API to track job and internship applications (Praktikum / Werkstudent / Junior roles).

Solo Spring Boot project — layered architecture (controller → service → repository), validation, global error handling, and automated tests.

## Tech stack

- Java 25
- Spring Boot 4
- Spring Web, Spring Data JPA, Validation
- H2 (in-memory) for local development
- JUnit 5, Mockito, MockMvc

## Features

- Full CRUD for applications (`/api/applications`)
- Filter by status: `GET /api/applications?status=INTERVIEW`
- Stats: `GET /api/applications/stats` (total + count per status)
- Validation (`@NotBlank` on company/role)
- Consistent JSON errors for 404 / 400 via `@RestControllerAdvice`
- Unit tests (service) + MockMvc test (validation → 400)

## Application statuses

`SAVED`, `APPLIED`, `INTERVIEW`, `OFFER`, `REJECTED`, `GHOSTED`

## Run

Prerequisites: JDK 25, Maven (or IntelliJ).

```bash
mvn spring-boot:run
```

Or run `ApplytrackApiApplication` from IntelliJ.

API base: `http://localhost:8080`

H2 console (optional): `http://localhost:8080/h2-console`  
(JDBC URL: `jdbc:h2:mem:applytrack`)

> Note: H2 is in-memory — data is lost after restart.

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
```

## Tests

```bash
mvn test
```

- `ApplicationServiceTest` — stats zeros, not-found exception (Mockito)
- `ApplicationControllerTest` — blank company → 400 (MockMvc)

## Author

Aleksandra Frej — [github.com/alexef360](https://github.com/alexef360)
