# Task Management System
A REST API application for managing tasks and their comments, supporting operations to create, update, view, and delete tasks. It also implements user registration, authentication, and authorization.

# Technology Stack
- Java 21
- Spring Boot 4.1.0
- Spring Data JPA + Redis
- Spring Security + JWT (io.jsonwebtoken, jjwt 0.13.0)
- Spring Security OAuth2 Client (login via GitHub and Google)
- Redis (spring-boot-starter-data-redis) — storage of one-time codes for OAuth2 exchange
- PostgreSQL
- Bean Validation (spring-boot-starter-validation)
- springdoc-openapi (Swagger UI)
- Lombok

# How to get started

1. Clone the repository from GitHub

 ```git clone https://github.com/SamirM-dev/8.RestSecurityTaskManagementSystem.git```

2. Set the environment variables

```
DB_URL=...
DB_USERNAME=...
...
JWT_SECRET=...
GITHUB_CLIENT_ID=...
...
```

**A list of all variables can be found in the .env.example file*

3. Build and run locally:
```
./mvnw package -DskipTests
java -jar target\RestSecurityTaskManagementSystem-0.0.1-SNAPSHOT.jar
```

# Documentation

- Complete interactive documentation: http://localhost:8080/swagger-ui.html
- Postman collection:
   - Import: Postman → Import → select the collection file
   - Create environment variables:
      - auth: `http://localhost:8080/api/v1/auth`
      - path: `http://localhost:8080/api/v1`
      - tasks: `http://localhost:8080/api/v1/tasks`
      - users: `http://localhost:8080/api/v1/users`

# Request Examples

- User Registration
```
curl -X POST http://localhost:8080/api/v1/auth/register \
  -H “Content-Type: application/json” \
  -d '{
    “name”: “string”,
    “email”: “user@example.com”,
    “password”: “stringst”
  }'
  ```
- Get a task by ID (JWT token required)
```
curl -X GET http://localhost:8080/api/v1/tasks/1 \
  -H “Authorization: Bearer <your_access_token>”
  ```

