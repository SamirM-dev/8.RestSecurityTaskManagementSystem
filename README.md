# Task Management System
A REST API application for managing tasks and their comments, supporting operations to create, update, view, and delete tasks. It also implements user registration, authentication, and authorization.

# Technology Stack
- Java 21
- Maven Wrapper is included — no separate Maven installation is required, just run `./mvnw` (or `mvnw.cmd` on Windows)
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

## Authentication Flow

**Register + login (email/password):**
1. `POST /api/v1/auth/register` — create an account (`name`, `email`, `password`)
2. `POST /api/v1/auth/login` — returns `{ accessToken, refreshToken }`
3. Use the token in every subsequent request: `Authorization: Bearer <accessToken>`
4. `POST /api/v1/auth/refresh` — when the access token expires, exchange the `refreshToken` for a new pair
5. `POST /api/v1/auth/logout` — invalidates the current refresh token

**Login via OAuth2 (GitHub):**
1. Open in browser: `GET /oauth2/authorization/github`
2. Log in and approve access on the provider's page
3. You'll be redirected to `http://localhost:3000/oauth2/callback?code=<one-time-code>`
4. Exchange the code for tokens: `POST /api/v1/auth/exchange` with `{ "code": "<one-time-code>" }` — returns `{ accessToken, refreshToken }`

**Note: /oauth2/callback requires a frontend page to handle the token exchange — for testing purposes, copy the code from the browser URL manually and call /exchange via Postman/curl.*

## Roles & Permissions

| Role | Can do |
|---|---|
| `ROLE_USER` | Create tasks, view/edit/comment on **own** tasks, view own profile |
| `ROLE_ADMIN` | Everything `ROLE_USER` can do, plus: view/edit any user's tasks, delete tasks, delete comments, access `/actuator/**` endpoints |

New users are assigned `ROLE_USER` by default (both on registration and on first OAuth2 login).

## Main Endpoints

| Method | Path | Auth required | Description |
|---|---|---|---|
| POST | `/api/v1/auth/register` | — | Register a new account |
| POST | `/api/v1/auth/login` | — | Log in, returns tokens |
| POST | `/api/v1/auth/refresh` | — | Refresh the access token |
| POST | `/api/v1/auth/exchange` | — | Exchange OAuth2 one-time code for tokens |
| GET | `/api/v1/me` | ✅ | Get current user info |
| GET | `/api/v1/tasks` | ✅ | List your own tasks (with filters & pagination) |
| POST | `/api/v1/tasks` | ✅ | Create a task |
| GET | `/api/v1/tasks/{id}` | ✅ (owner or ADMIN) | Get a task by id |
| PUT | `/api/v1/tasks/{id}` | ✅ (owner or ADMIN) | Update a task |
| DELETE | `/api/v1/tasks/{id}` | ✅ (ADMIN only) | Delete a task |
| GET | `/api/v1/tasks/{taskId}/comments` | ✅ | List comments for a task |

**Full list of endpoints with request/response schemas: see Swagger UI.*

# Request Examples

- User Registration
```
curl -X POST http://localhost:8080/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "string",
    "email": "user@example.com",
    "password": "stringst"
  }'
  ```
- Get a task by ID (JWT token required)
```
curl -X GET http://localhost:8080/api/v1/tasks/1 \
  -H “Authorization: Bearer <your_access_token>”
  ```

