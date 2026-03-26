# 🏥 DocFlow — Hospital Management System

> A production-ready REST API backend for managing patients, doctors, and appointments in a hospital — built to be deployed, not just demoed.

---

## Architecture

```
                    ┌─────────────────┐
                    │  React Frontend  │  (Module 14)
                    └────────┬────────┘
                             │ HTTP/JSON
                    ┌────────▼────────┐
                    │   Controller    │  @RestController
                    │  /api/patients  │  Receives requests
                    └────────┬────────┘
                             │
                    ┌────────▼────────┐
                    │    Service      │  Business logic
                    │  PatientService │  (validation, rules)
                    └────────┬────────┘
                             │
                    ┌────────▼────────┐
                    │   Repository    │  Spring Data JPA
                    │ JpaRepository   │  Auto-generated SQL
                    └────────┬────────┘
                             │ SQL
                    ┌────────▼────────┐
                    │   PostgreSQL    │  Running in Docker
                    │   (port 5432)   │
                    └─────────────────┘
```

---

## What It Does

A REST API backend that lets hospital staff:
- Register and manage **patients** (name, age, gender, phone, address)
- Manage **doctors** and their specializations
- Book and track **appointments** between patients and doctors
- Login securely via **JWT tokens**
- Access only what their **role** permits (Admin, Doctor, Patient)

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 3.4.3 |
| ORM | Spring Data JPA + Hibernate |
| Database | PostgreSQL 16 (Dockerized) |
| Security | Spring Security + JWT |
| Frontend | React + Tailwind CSS (coming) |
| Docs | Swagger / OpenAPI (coming) |
| Containerization | Docker |

---

## Technical Decisions

**Why Spring Boot over plain Java JDBC?**
The previous version of this project used raw JDBC — manually writing SQL strings, managing connections, and handling ResultSets. Spring Boot with JPA eliminates all of that. A `patientRepository.save(patient)` replaces 15 lines of JDBC code.

**Why PostgreSQL over MySQL?**
PostgreSQL is the industry standard for production backends, has better support in Spring Boot's JPA ecosystem, and integrates cleanly with Docker.

**Why the Controller-Service-Repository pattern?**
Keeps concerns separated. Controllers are thin (just receive/return JSON). Services own all business logic. Repositories only touch the database. This makes each layer independently testable.

---

## Running Locally

### Prerequisites
- Java 17
- Maven 3.9+
- Docker Desktop

### 1. Start the database
```bash
docker run --name docflow-db \
  -e POSTGRES_PASSWORD=docflow123 \
  -e POSTGRES_DB=docflow \
  -p 5432:5432 -d postgres
```

### 2. Run the application
```bash
./mvnw spring-boot:run
```

### 3. API is live at
```
http://localhost:8080/api/patients
http://localhost:8080/api/doctors
http://localhost:8080/api/appointments
```

---

## Features

- [x] Spring Boot project setup
- [x] Entity model: Patient, Doctor, Appointment
- [x] Patient CRUD REST APIs
- [ ] Doctor module
- [ ] Appointment booking
- [ ] JWT Authentication
- [ ] Role-based access (Admin, Doctor, Patient)
- [ ] Search & Pagination
- [ ] Swagger API Docs
- [ ] React Frontend

---

## Author

**Hiten Verma** — [GitHub](https://github.com/HitenVerma04) · [hitenverma16@gmail.com](mailto:hitenverma16@gmail.com)
