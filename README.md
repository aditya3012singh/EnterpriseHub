# 🏢 Enterprise Hub

A production‑grade Service Marketplace Platform inspired by UrbanCompany, built to demonstrate industry‑level backend engineering, clean architecture, and real‑world system design.

This repository is a learning‑plus‑portfolio project focusing on Low Level Design (LLD), High Level Design (HLD), security, scalability, and clear domain modeling — not just CRUD APIs.

---

## 🚀 What is Enterprise Hub?

Enterprise Hub connects customers with vetted service providers (electricians, plumbers, cleaners, etc.) and supports the full lifecycle:

- User authentication & role management
- Provider onboarding & verification
- Service listing & pricing
- Booking and time‑slot conflict detection
- Provider acceptance / rejection & booking state machine
- Secure payment flow (Razorpay)
- Role‑based access control and method‑level security

---

## 🧠 Core Learning Goals

This project is designed to teach and demonstrate:

- Low Level Design (LLD) and domain modelling
- High Level Design (HLD) for backend systems
- Spring Security (JWT + Roles)
- Transaction boundaries and consistency
- Payment gateway integration and state transitions
- Production‑ready backend patterns and testing

---

## 🛠️ Tech Stack

Backend
- Java 17
- Spring Boot
- Spring Security (JWT)
- Spring Data JPA / Hibernate
- PostgreSQL
- Lombok
- Maven (or use the included wrapper ./mvnw)

Infrastructure & Integrations
- Razorpay (payment gateway)
- RESTful APIs
- Docker (for local DB / dev environment)

---

## 🧩 High‑Level Architecture

Client
  ↓
Controller Layer (REST)
  ↓
Service Layer (business logic, transactions)
  ↓
Repository Layer (Spring Data JPA)
  ↓
PostgreSQL

Security
- JWT Authentication Filter
- Role-based Authorization
- Method-level checks (`@PreAuthorize`)

---

## 👥 User Roles

| Role     | Description                          |
| -------- | ------------------------------------ |
| USER     | Customer booking services            |
| PROVIDER | Service provider fulfilling bookings |
| ADMIN    | Platform administration              |

Roles are persisted in the database and embedded inside JWT as authorities (prefixed with `ROLE_`).

---

## 📦 Domain Model (Phase‑wise)

Phase 1 – Identity & Profiles
- User
- Role
- ProviderProfile
- CustomerProfile

Phase 2 – Services & Bookings
- Service
- ProviderService (mapping)
- Booking

Phase 3 – Payments
- Payment (one-to-one with Booking)

Key concepts:
- Enum‑based statuses and state machine for bookings
- Provider availability and booking conflict detection
- Secure payment verification and booking status transitions

---

## 📅 Booking Flow

1. Customer selects a service and a preferred time slot.  
2. System checks provider availability and creates a PENDING booking.  
3. Provider accepts or rejects the booking. On accept → CONFIRMED.  
4. Customer initiates payment for CONFIRMED bookings (Razorpay order).  
5. Payment verified server‑side → Booking transitions to PAID.  
6. Duplicate payments are prevented and lifecycle is handled transactionally.

---

## 💳 Payment Flow (Razorpay)

- Payments are created only for CONFIRMED bookings.
- Use secure signature verification on payment callbacks.
- Payment lifecycle is coupled to Booking state transitions with transactional boundaries.

Required (example) environment variables:
- RAZORPAY_KEY_ID
- RAZORPAY_KEY_SECRET
- RAZORPAY_WEBHOOK_SECRET

---

## 🔐 Authentication & Security

- JWT‑based stateless authentication
- Custom `UserDetails` implementation and service
- Roles prefixed with `ROLE_`
- Controller & method level security (`@PreAuthorize("hasRole('PROVIDER')")`)
- CSRF disabled for stateless APIs; secure endpoints using HTTPS in production

---

## 🧪 Local Development

Prerequisites
- Java 17
- Maven (or use `./mvnw`)
- Docker (recommended for local Postgres)

Quick start (with Docker Compose)
1. Start Postgres:
   - docker run --name enterprisehub-postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=enterprisehub -p 5432:5432 -d postgres:15
   or use a provided docker-compose.yml if present.

2. Configure environment variables (example `.env` or application.yml):
   - SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/enterprisehub
   - SPRING_DATASOURCE_USERNAME=postgres
   - SPRING_DATASOURCE_PASSWORD=postgres
   - JWT_SECRET=your_jwt_secret
   - RAZORPAY_KEY_ID=...
   - RAZORPAY_KEY_SECRET=...

3. Build and run:
   - ./mvnw clean package
   - java -jar target/*.jar
   or
   - ./mvnw spring-boot:run

4. API docs: If Swagger/OpenAPI is enabled, visit `/swagger-ui.html` or `/swagger-ui/index.html`.

---

## 🧰 Testing

- Unit tests: mvn test
- Integration tests: runs against an embedded DB or a test Postgres instance (check `application-test.yml`)
- When adding tests, prefer small, isolated units for services and repository integration tests for DB behaviour.

---

## 📁 Recommended Project Layout

com.aditya.enterprisehub
- config
  - security
  - auth
- controllers
- services
- repository
- entity
  - enums
  - common
- payment
- utils

Follow package boundaries: controllers → services → repositories. Keep DTOs and mappers close to controllers or a dedicated `dto` package.

---

## ✅ Current Status

- Phase 1 – Identity & Profiles: Completed
- Phase 2 – Services & Bookings: Completed
- Phase 3 – Payments: Completed
- Phase 4 – Planned: Chat (WebSocket), Notifications, Admin dashboards, Kafka-based async events

---

## 📈 Why this project matters

This is not a toy app. It demonstrates:
- Real business rules and domain-driven design
- Proper transactional boundaries and error handling
- Production-ready authentication, authorization, and payment flows
- Design choices that are interview- and portfolio-ready

---

## 🛠️ Contribution & Roadmap

Contributions welcome — open an issue or PR for:
- Chat & notifications
- Admin dashboards and metrics
- Event-driven architecture with Kafka
- Dockerized deployments and CI/CD

---

## 📌 Author

**Aditya Singh**  
B.Tech (4th Semester)  
Backend‑focused Software Engineer — aditya3012singh

---

## ⭐ Final Note

This project is meant to evolve. Future upgrades include:
- Microservices split
- Kafka event streaming
- Dockerized deployment & production Helm charts
- Enhanced monitoring and observability

> "Build fewer projects. Build them deeper."
