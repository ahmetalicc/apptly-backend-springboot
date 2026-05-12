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
