# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Commands

```bash
# Run the application
./mvnw spring-boot:run

# Build (compile + test)
./mvnw package

# Build skipping tests
./mvnw package -DskipTests

# Run all tests
./mvnw test

# Run a single test class
./mvnw test -Dtest=ApiApplicationTests

# Compile only (fast check for errors)
./mvnw compile
```

> The app requires a running PostgreSQL instance at `localhost:5432` (database: `nexbank_dev`). Tests annotated with `@SpringBootTest` will also try to connect.

## Architecture

**Layer flow:** `Controller → Service → Repository (JPA) → PostgreSQL`

All routes are versioned under `/api/v1/`. The three controllers map directly to three services: `UserService`, `AccountService`, `TransactionService`.

**Domain entities** (`domain/`) are JPA-annotated and live in the `banking` schema. Flyway manages schema evolution — migrations live in `src/main/resources/db/migration/` and follow the `V{n}__{description}.sql` naming convention. `spring.jpa.hibernate.ddl-auto=validate` means Hibernate validates against the Flyway-created schema; never use `create` or `update`.

**Request/response separation:** Controllers accept `controller/request/` objects (validated with `@Valid`), services produce DTOs from `dto/`, and `mapper/` interfaces (MapStruct) handle all conversions between layers. Domain objects are never returned directly from controllers.

**Exception hierarchy:** All domain exceptions extend `NexBankException`, which carries an HTTP status code. `GlobalExceptionHandler` (`error/`) handles three cases: `NexBankException` (domain errors), `MethodArgumentNotValidException` (Bean Validation failures), and the generic `Exception` fallback. Adding a new domain exception requires only extending `NexBankException` — no changes to the handler.

**Monetary values** use `BigDecimal` throughout. Never use `double` or `float` for financial amounts.

**Transfers** (`TransactionService.transfer`) are `@Transactional` and create two `Transaction` records atomically: `TRANSFER_OUT` on the source account and `TRANSFER_IN` on the target.

## Key design notes for Phase 3+ work

- `LoginRequest` and `LoginResponseDTO` are already scaffolded for JWT auth — Spring Security integration is the next phase.
- `UserRepositoryMemory`, `BankAccountRepositoryMemory`, and `TransactionRepositoryMemory` are commented out (Phase 1 relics). The active repositories extend `JpaRepository`.
- Lombok annotation processor must be declared **before** MapStruct in `pom.xml` — this ordering is required for MapStruct to see Lombok-generated methods.
