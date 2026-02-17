# qa-microservice-test-harness

Java microservice with a REST API and a complete integration testing setup based on real infrastructure.

This repository contains a minimal backend service whose primary purpose is to demonstrate how a service is validated and tested, rather than to provide business features.

---

## Overview

The application exposes a small REST API for managing tasks and persists data in a PostgreSQL database.

The repository includes:

- the service implementation
- database schema migrations
- integration tests executed against a real database
- a CI pipeline enforcing formatting, testing, and coverage rules

All environments (local, test, CI) behave consistently.

---

## Architecture

- Spring Boot REST application
- PostgreSQL as the persistence layer
- Flyway for database schema management
- JPA / Hibernate with schema validation enabled
- Integration tests executed against real infrastructure

No in-memory databases or mocked persistence layers are used.

---

## Technology Stack

- Java 21
- Spring Boot
- PostgreSQL
- Flyway
- JPA / Hibernate
- JUnit 5
- RestAssured
- Testcontainers
- Docker / Podman
- Maven Wrapper
- Spotless
- JaCoCo
- GitHub Actions

---

## API Description

### Health check

**GET** `/health`

Response:

```json
{ "status": "ok" }
Create task (idempotent)

POST /tasks

Request body:

{ "title": "Task description" }

Constraints:

title is required

title must not be empty or blank

Optional header:

Idempotency-Key: <string>

Behavior:

same idempotency key with the same payload returns the same result

same idempotency key with a different payload returns 409 Conflict

idempotency state is persisted in the database

List tasks

GET /tasks

Query parameters:

limit (default 20, min 1, max 100)

offset (default 0)

search (optional, case-insensitive)

Returns a paginated list of tasks matching the provided filters.

Get task by ID

GET /tasks/{id}

Responses:

200 OK if the task exists

404 Not Found if the task does not exist

404 response body:

{ "detail": "Task not found" }
Database & Migrations

The service always runs against a PostgreSQL database.

Schema changes are defined exclusively through Flyway migrations

Hibernate is configured with ddl-auto=validate

Application startup fails if the schema does not match the entity model

This guarantees explicit and versioned database structure.

Testing

The repository includes a full suite of integration tests.

Tests verify:

HTTP API behavior

database persistence

Flyway migration execution

idempotency guarantees

pagination and search behavior

error handling

PostgreSQL is started automatically using Testcontainers during test execution.
No external services are required to run the tests.

Continuous Integration

A GitHub Actions workflow is configured to run on each push.

The pipeline:

executes the full test suite

checks code formatting with Spotless

enforces minimum coverage thresholds using JaCoCo

The build fails if any check does not pass.

Project Scope

The application is intentionally minimal.

The scope is limited to:

realistic backend behavior

reproducible environments

reliable integration testing

Additional features (authentication, messaging, async processing, etc.) are intentionally out of scope.
