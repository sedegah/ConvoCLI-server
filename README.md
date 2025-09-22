# ConvoCLI – Server

A Spring Boot–based backend that powers the **ConvoCLI** ecosystem.
This server provides secure RESTful APIs and WebSocket endpoints for real-time messaging, conversation management, and user authentication built for seamless integration with the ConvoCLI client.

---

## Features

* **REST + WebSocket APIs** – Real-time messaging and conversation endpoints.
* **Spring Security** – JWT-based authentication and role management.
* **Modular Architecture** – Clear separation of config, controller, service, and repository layers.
* **Database Ready** – Works with PostgreSQL, MySQL, or H2 for development.
* **Production Friendly** – Easily deployable as a single JAR or to container platforms.

---

## Project Structure

```
ConvoCLI-server/
├─ src/main/java/io/convocli/
│  ├─ config/        # App configuration & security setup
│  ├─ controller/    # REST controllers
│  ├─ model/         # Entity classes
│  ├─ repository/    # Spring Data JPA repositories
│  ├─ security/      # JWT filters & authentication logic
│  ├─ service/       # Business services
│  └─ ws/            # WebSocket handlers
│  └─ Application.java
├─ src/main/resources/
│  ├─ application.yml  # Environment-specific settings
│  └─ static/          # Static assets if needed
└─ target/             # Compiled build output
```

---

## Prerequisites

* **Java 17+** (or the version defined in your `pom.xml`)
* **Maven 3.8+**
* Database (PostgreSQL/MySQL) or H2 for development.

---

## Installation & Running

### 1. Clone the Repository

```bash
git clone https://github.com/sedegah/ConvoCLI-server.git
cd ConvoCLI-server
```

### 2. Configure Environment

Edit `src/main/resources/application.yml` (or `.properties`) to set:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/convocli
    username: your_db_user
    password: your_db_password
  jwt:
    secret: your_jwt_secret
```

### 3. Build & Run

```bash
mvn clean install
java -jar target/ConvoCLI-server-0.0.1-SNAPSHOT.jar
```

The API will start on: **[http://localhost:8080](http://localhost:8080)**

---

## API Endpoints (Examples)

| Method | Endpoint             | Description                    |
| ------ | -------------------- | ------------------------------ |
| POST   | `/api/auth/login`    | Authenticate and get JWT token |
| GET    | `/api/conversations` | List conversations             |
| POST   | `/api/messages`      | Send a message                 |
| WS     | `/ws/messages`       | WebSocket real-time messaging  |

Swagger/OpenAPI documentation (if enabled) is available at:
`http://localhost:8080/swagger-ui.html`

---

## Author

**Kimathi Elikplim Sedegah**
GitHub: [@sedegah](https://github.com/sedegah)

---

