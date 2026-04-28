# Internship Tracker — Backend

A production-style REST API for tracking internship applications, built with Spring Boot and PostgreSQL. Live and deployed on Railway.

**Live API:** `https://internshiptracker-production.up.railway.app`  
**Frontend:** [internshiptracker-frontend.vercel.app](https://internshiptracker-frontend.vercel.app)

---

## Tech Stack

| Layer | Technology |
|---|---|
| Framework | Spring Boot 4.0 (Java 17) |
| Database | PostgreSQL |
| Security | Spring Security + JWT |
| Payments | Stripe |
| Email | SendGrid |
| Deployment | Railway + Docker |
| Build Tool | Gradle |

---

## Features

- **JWT Authentication** — stateless auth with BCrypt password hashing
- **Multi-user data isolation** — users can only access their own data
- **Application tracking** — full CRUD with pagination and sorting
- **Company normalization** — companies stored as a separate entity with `findOrCreate` pattern
- **CRM-style interaction timeline** — log interviews, emails, and notes per application
- **Freemium model** — free tier (30 applications), Pro tier via one-time Stripe payment
- **Feedback system** — bug reports and feature requests with SendGrid email notifications
- **Global exception handling** — proper HTTP status codes for all error cases

---

## Architecture

```
Controller → Service → Repository
```

- **Controllers** handle HTTP requests and responses only
- **Services** contain business logic, ownership checks, and tier enforcement
- **Repositories** handle all database operations via Spring Data JPA

---

## API Endpoints

### Auth
```
POST /auth/register     — create account
POST /auth/login        — returns JWT token
```

### Applications
```
GET    /applications                    — paginated list (authenticated)
POST   /applications                    — create application
GET    /applications/{id}               — get by ID
PUT    /applications/{id}/status        — update status
DELETE /applications/{id}               — delete
```

### Interactions
```
GET    /applications/{id}/interactions  — get timeline
POST   /applications/{id}/interactions  — log interaction
DELETE /applications/{id}/interactions/{interactionId}
```

### Companies
```
GET /companies       — list all companies
GET /companies/{id}  — get by ID
```

### Payments
```
POST /payments/create-checkout-session  — create Stripe checkout
POST /payments/webhook                  — Stripe webhook handler
```

### Feedback
```
POST /feedback  — submit bug report or feature request
```

---

## Running Locally

### Prerequisites
- Java 17
- PostgreSQL
- Gradle

### Setup

1. Clone the repo
```bash
git clone https://github.com/costell-j/internshiptracker.git
cd internshiptracker
```

2. Create a PostgreSQL database
```sql
CREATE DATABASE internshiptracker;
```

3. Set environment variables (or update `application.properties` with local values)
```
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/internshiptracker
SPRING_DATASOURCE_USERNAME=username
SPRING_DATASOURCE_PASSWORD=password
JWT_SECRET=secret_key
JWT_EXPIRATION=86400000
STRIPE_SECRET_KEY=sk_test_...
STRIPE_WEBHOOK_SECRET=whsec_...
SENDGRID_API_KEY=SG...
SENDGRID_FROM_EMAIL=blank@email.com
FEEDBACK_RECIPIENT_EMAIL=blank@email.com
FRONTEND_URL=http://localhost:5173
```

4. Run the application
```bash
./gradlew bootRun
```

The API will be available at `http://localhost:8080`.

---

## Deployment

The backend is containerized with Docker and deployed to Railway. Every push to `main` triggers an automatic redeploy.

```dockerfile
FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app
COPY . .
RUN ./gradlew bootJar --no-daemon

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

All secrets are stored as Railway environment variables — nothing sensitive is in the codebase.

---

## Database Schema

```
users
  id, email, password, name, tier

applications
  id, role, status, location, applied_date, notes, user_id, company_id

companies
  id, name, website, industry, location

interactions
  id, type, notes, occurred_at, application_id

feedback
  id, type, message, submitted_by_email, submitted_at
```

---

## Author

**Costell Johnson**  
[LinkedIn](https://www.linkedin.com/in/costell-johnson-815778343) · [GitHub](https://github.com/costell-j)
