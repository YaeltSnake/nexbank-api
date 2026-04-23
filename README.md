# nexbank-api

> RESTful Banking API built with Spring Boot — designed as a living portfolio project that grows alongside real-world backend and application security skills.

![Status](https://img.shields.io/badge/status-active%20development-brightgreen)
![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.13-green)
![Phase](https://img.shields.io/badge/phase-1%20complete-blue)

---

## About

NexBank simulates a banking backend API. Built incrementally — starting with clean architecture fundamentals and progressively incorporating production-grade features like database persistence, authentication, and application security practices used in fintech environments.

Every design decision has a reason. The goal is not just a working API, but code that reflects how a backend developer thinks: separation of concerns, defensive programming, and security awareness at every layer.

---

## Current Features — Phase 1

- **Layered architecture** — Controller → Service → Repository → Domain
- **DTO pattern** — immutable request/response objects with dedicated MapStruct mappers
- **In-memory repositories** — interface-based design ready for zero-friction JPA migration
- **Domain-driven exceptions** — `NexBankException` base class with HTTP status embedded
- **GlobalExceptionHandler** — centralized error handling including Bean Validation errors
- **Bean Validation** — input validation on all request objects (`@Valid`, `@NotBlank`, `@Email`, `@DecimalMin`)
- **AccountOperationDTO** — balance before/after tracking on every financial operation
- **Versioned endpoints** — all routes under `/api/v1/`
- **Dual transaction records** — transfers create `TRANSFER_OUT` + `TRANSFER_IN` entries atomically

---

## API Endpoints

### Users
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/v1/users` | Register a new user |
| GET | `/api/v1/users/{id}` | Get user by ID |
| GET | `/api/v1/users` | Get all users |
| DELETE | `/api/v1/users/{id}` | Delete user |

### Accounts
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/v1/accounts` | Create bank account |
| GET | `/api/v1/accounts/{id}` | Get account details |
| GET | `/api/v1/accounts/user/{userId}` | Get accounts by user |
| POST | `/api/v1/accounts/{id}/deposit` | Deposit funds |
| POST | `/api/v1/accounts/{id}/withdraw` | Withdraw funds |

### Transactions
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/v1/transactions/transfer` | Transfer between accounts |
| GET | `/api/v1/transactions/account/{accountId}` | Get transaction history |

---

## Project Structure

```
com.nexbank.api
├── controller
│   ├── request        # Immutable input contracts with Bean Validation
│   └── response       # (Phase 3: JWT responses)
├── domain             # Core entities: User, BankAccount, Transaction
├── dto                # API output contracts — never expose domain directly
├── enums              # AccountType, AccountStatus, TransactionType
├── error              # ErrorResponse + GlobalExceptionHandler
├── exception          # NexBankException hierarchy
├── mapper             # MapStruct interfaces — zero boilerplate mapping
├── repository         # Interfaces + in-memory implementations
└── service            # Business logic layer
```

---

## Design Decisions

**BigDecimal for monetary values** — `double` and `float` have floating-point precision issues that make them unsafe for financial calculations. `BigDecimal` provides exact decimal arithmetic.

**NexBankException base class** — each domain exception carries its own HTTP status code. The `GlobalExceptionHandler` needs a single method to handle all domain errors, regardless of how many exceptions are added in the future.

**Interface-based repositories** — `UserRepository` is an interface. `UserRepositoryMemory` is the current implementation. When JPA arrives in Phase 2, swapping implementations requires zero changes to the service layer.

**Immutable DTOs** — `@Getter` + `@Builder`, no `@Setter`. A DTO built once cannot be mutated in transit. Defensive programming from the ground up.

**Dual transaction records on transfer** — a transfer between accounts A → B creates two `Transaction` records: `TRANSFER_OUT` on A and `TRANSFER_IN` on B. Each account has a complete, self-contained history.

---

## Roadmap

### Phase 2 — Persistence
- [ ] PostgreSQL + Spring Data JPA
- [ ] Flyway schema migrations
- [ ] Replace `*RepositoryMemory` with JPA repositories (zero service-layer changes)
- [ ] `@Transactional` on transfer operations

### Phase 3 — Authentication
- [ ] JWT-based authentication (`LoginRequest` + `LoginResponseDTO` already scaffolded)
- [ ] Spring Security integration
- [ ] Role-based access control: `ADMIN` / `CLIENT`

### Phase 4 — Application Security (AppSec)
- [ ] BCrypt password hashing
- [ ] Strong password validation with `@Pattern`
- [ ] Rate limiting on sensitive endpoints
- [ ] Audit logging for all financial transactions
- [ ] OWASP Top 10 review applied per endpoint
- [ ] Secure headers (HSTS, CSP, X-Frame-Options)
- [ ] Secret management — no hardcoded credentials

### Phase 5 — Production Readiness
- [ ] Structured logging with correlation IDs
- [ ] Spring Actuator — health checks and metrics
- [ ] Docker + docker-compose
- [ ] OpenAPI / Swagger documentation
- [ ] UUID migration for entity IDs

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 3.5.13 |
| Build | Maven |
| Mapping | MapStruct 1.5.5 + Lombok |
| Validation | Jakarta Bean Validation |
| Persistence (current) | In-memory (Collections) |
| Persistence (Phase 2) | PostgreSQL + Spring Data JPA |

---

## Running the Project

```bash
git clone https://github.com/YaeltSnake/nexbank-api.git
cd nexbank-api/api
./mvnw spring-boot:run
```

API available at `http://localhost:8080`

---

## Author

Backend developer building toward fintech-grade application security. This project documents a learning path from REST fundamentals to production-ready, secure banking APIs.
