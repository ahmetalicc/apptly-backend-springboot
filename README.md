# apptly-backend-springboot

Short Description

A backend service for an appointment scheduling system built with Spring Boot. Provides role-based access, JWT authentication, and APIs to manage appointments and users.

---

## Installation

Prerequisites:
- Java 17+
- Maven
- MySQL (or configured database)

Steps:
1. Clone the repository:

```bash
git clone https://github.com/ahmetalicc/apptly-backend-springboot.git
cd apptly-backend-springboot
```

2. Configure environment variables or `application.properties` with your database credentials and JWT settings.

3. Build and run:

```bash
mvn clean install
mvn spring-boot:run
```

The application will start on http://localhost:8080 by default.

---

## Usage

- Use the provided REST endpoints to authenticate and manage appointments.
- Example: obtain a JWT via `/auth/login` and include it in the `Authorization: Bearer <token>` header for protected endpoints.

Refer to the code and controller classes for a full list of endpoints.

---

## API Endpoints (Detailed)

All endpoints requiring authentication expect an `Authorization` header:

Authorization: Bearer <JWT_TOKEN>

Base URL (local): http://localhost:8080

---

### Authentication

- POST /auth/login
  - Description: Authenticate user and return JWT token.
  - Request:
    ```json
    {
      "username": "user@example.com",
      "password": "secret"
    }
    ```
  - Response (200):
    ```json
    {
      "token": "<JWT_TOKEN>"
    }
    ```
  - Errors:
    - 401 Unauthorized — invalid credentials
  - Notes: Use returned token in `Authorization` header for protected endpoints.

- POST /auth/register
  - Description: Create a new user (if registration endpoint exists in codebase).
  - Request:
    ```json
    {
      "firstName": "John",
      "lastName": "Doe",
      "username": "john@example.com",
      "password": "password123"
    }
    ```
  - Response:
    - 201 Created — user created
    - 400 Bad Request — validation errors

- POST /auth/refresh
  - Description: Refresh JWT token (if supported).
  - Request: include refresh token as configured (body or cookie).
  - Response: new JWT token.

---

### Users

- GET /users
  - Description: List users (paginated).
  - Query params: `page`, `size`, `sort`
  - Auth: ROLE_ADMIN or appropriate role
  - Response (200): paginated list of users.

- GET /users/{id}
  - Description: Get user by id.
  - Auth: ROLE_ADMIN or user themself
  - Response (200): user object
  - Errors: 404 Not Found

- POST /users
  - Description: Create a user (admin or registration flow).
  - Auth: may be public (register) or ROLE_ADMIN.
  - Request:
    ```json
    {
      "firstName": "Jane",
      "lastName": "Doe",
      "username": "jane@example.com",
      "password": "p@ssw0rd"
    }
    ```
  - Response: 201 Created

- PUT /users/{id}
  - Description: Update user.
  - Auth: ROLE_ADMIN or the user.
  - Response: 200 OK

- DELETE /users/{id}
  - Description: Delete user.
  - Auth: ROLE_ADMIN
  - Response: 204 No Content

---

### Appointments

- GET /appointments
  - Description: List appointments. Supports filters.
  - Query params:
    - `page`, `size`, `sort`
    - `userId` (optional) — filter by user
    - `date` or `from`/`to` (optional)
  - Auth: ROLE_ADMIN, ROLE_PROVIDER, or the appointment owner (depending on implementation)
  - Response: paginated appointments

- GET /appointments/{id}
  - Description: Get appointment details.
  - Auth: owner or admin/provider
  - Response: appointment object

- POST /appointments
  - Description: Create a new appointment.
  - Request:
    ```json
    {
      "userId": 42,
      "providerId": 7,
      "startTime": "2026-06-01T14:30:00",
      "endTime": "2026-06-01T14:45:00",
      "reason": "Consultation"
    }
    ```
  - Response: 201 Created with created appointment object
  - Errors:
    - 400 Bad Request — invalid payload
    - 409 Conflict — time slot unavailable

- PUT /appointments/{id}
  - Description: Update appointment (time, status, notes).
  - Auth: owner or admin/provider
  - Response: 200 OK

- PATCH /appointments/{id}/status
  - Description: Change appointment status (e.g., confirm, cancel).
  - Request:
    ```json
    { "status": "CANCELLED" }
    ```
  - Response: 200 OK

- DELETE /appointments/{id}
  - Description: Cancel/delete an appointment.
  - Auth: owner or admin
  - Response: 204 No Content

- GET /appointments/available
  - Description: Query available slots for a provider (if implemented).
  - Query params: `providerId`, `date`, `duration`
  - Response: list of available time slots

---

### Roles & Authorization

- Role-protected endpoints require role checks. Typical roles:
  - ROLE_ADMIN — full access (users, appointments)
  - ROLE_PROVIDER / LABORANT — manage appointments assigned to provider
  - ROLE_USER — manage own appointments
- Example: endpoints that modify other users’ data (DELETE /users/{id}, GET /users) should be ROLE_ADMIN only.

---

### Example cURL requests

- Login
```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin@example.com","password":"adminpass"}'
```

- Create appointment
```bash
curl -X POST http://localhost:8080/appointments \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <TOKEN>" \
  -d '{
    "userId": 10,
    "providerId": 5,
    "startTime": "2026-06-01T14:00:00",
    "endTime": "2026-06-01T14:30:00",
    "reason": "Checkup"
  }'
```

---

### Error codes & conventions
- 200 OK — successful GET/PUT/PATCH
- 201 Created — resource created (POST)
- 204 No Content — successful delete
- 400 Bad Request — validation or malformed input
- 401 Unauthorized — missing/invalid JWT
- 403 Forbidden — insufficient role/permission
- 404 Not Found — resource not found
- 409 Conflict — business rule conflict (e.g., overlapping appointment)

---

### Configuration (important env / application.properties)
- `spring.datasource.*` — DB connection settings (URL, username, password)
- `jwt.secret` or equivalent — JWT signing secret
- `jwt.expiration` — token lifetime
- `server.port` — port override
- Provide `.env.example` or `application.properties.example` with placeholders for local setup.

---

## Contributing

Contributions are welcome. Please follow these steps:
1. Fork the repository
2. Create a branch: `git checkout -b my-feature`
3. Commit your changes: `git commit -m "Add feature"`
4. Push to your fork and open a Pull Request

Please keep commits small and include tests where appropriate.

---

## License

This project is licensed under the MIT License. See the `LICENSE` file for details.

---

## Owner

Ahmet Alıç — https://github.com/ahmetalicc — ahmetalicswe@gmail.com
