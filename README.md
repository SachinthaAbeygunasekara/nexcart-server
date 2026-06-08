# NexCart Server

NexCart Server is the backend application for the **NexCart E-Commerce Platform**. It is built using **Spring Boot** and provides secure authentication, authorization, and data management services for the platform.

---

## 🚀 Project Status

🚧 **Currently in Development**

### Implemented Features

* User Registration
* User Authentication using JWT
* Role-Based Authorization
* Spring Security Configuration
* Password Encryption with BCrypt

### Planned Features

* Product Management
* Category Management
* Shopping Cart
* Order Management
* Inventory Management
* Payment Processing
* Customer Reviews & Ratings
* Sales Reporting & Analytics

---

## ✨ Features

### Authentication & Authorization

* Secure User Registration
* JWT-Based Authentication
* Role-Based Access Control (RBAC)
* BCrypt Password Encryption
* Stateless Session Management

### Security

* Spring Security Integration
* Protected API Endpoints
* JWT Authentication Filter
* Custom User Details Service

### Data Management

* MySQL Database Integration
* Spring Data JPA Repositories
* Hibernate ORM

### Validation

* Request Validation using Spring Validation

---

## 🛠 Tech Stack

| Technology     | Description                 |
| -------------- | --------------------------- |
| Java           | 17                          |
| Framework      | Spring Boot                 |
| Security       | Spring Security             |
| Authentication | JWT (JJWT)                  |
| Database       | MySQL                       |
| ORM            | Hibernate / Spring Data JPA |
| Build Tool     | Maven                       |
| Utilities      | Lombok                      |

---

## 🏗 Architecture

NexCart Server follows a layered architecture:

```text
Client
   │
   ▼
Controller Layer
   │
   ▼
Service Layer
   │
   ▼
Repository Layer
   │
   ▼
Database
```

### Security Layer

```text
User Login
    │
    ▼
AuthenticationManager
    │
    ▼
CustomUserDetailsService
    │
    ▼
JWT Token Generation
    │
    ▼
Client Receives Token
    │
    ▼
Authorization: Bearer <token>
    │
    ▼
JwtAuthenticationFilter
    │
    ▼
Protected Resources
```

---

## 👥 User Roles

| Role       | Description          |
| ---------- | -------------------- |
| ROLE_ADMIN | System Administrator |
| ROLE_USER  | Customer/User        |

---

## 📋 Prerequisites

Before running the application, ensure the following software is installed:

* JDK 17
* Maven
* MySQL Server

---

## ⚙️ Database Configuration

### Create Database

```sql
CREATE DATABASE nexcart;
```

### Configure Application Properties

Update:

```text
src/main/resources/application.properties
```

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/nexcart
spring.datasource.username=your_username
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

## ▶️ Running the Application

### Clone Repository

```bash
git clone https://github.com/your-username/nexcart-server.git
cd nexcart-server
```

### Run Application

Linux / macOS:

```bash
./mvnw spring-boot:run
```

Windows:

```cmd
mvnw.cmd spring-boot:run
```

The server will start on:

```text
http://localhost:8080
```

---

## 🔐 API Endpoints

### Authentication APIs

| Method | Endpoint         | Description                             |
| ------ | ---------------- | --------------------------------------- |
| POST   | `/auth/register` | Register a new user                     |
| POST   | `/auth/login`    | Authenticate user and receive JWT token |

---

### Register User

#### Request

```http
POST /auth/register
```

```json
{
  "username": "johndoe",
  "password": "password123",
  "role": "ROLE_USER"
}
```

---

### Login User

#### Request

```http
POST /auth/login
```

```json
{
  "username": "johndoe",
  "password": "password123"
}
```

#### Response

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

---

## 🔒 Accessing Protected APIs

Include the JWT token in the Authorization header:

```http
Authorization: Bearer <jwt-token>
```

Example:

```http
GET /products
Authorization: Bearer <jwt-token>
```

---

## 📁 Project Structure

```text
com.nexcart
│
├── config
│   ├── SecurityConfig.java
│   └── PasswordEncoderConfig.java
│
├── controller
│   ├── AuthController.java
│   └── ProductController.java
│
├── dto
│   ├── LoginRequest.java
│   └── AuthResponse.java
│
├── entity
│   └── User.java
│
├── repository
│   └── UserRepository.java
│
├── security
│   ├── JwtAuthenticationFilter.java
│   ├── JwtService.java
│   ├── UserDetailsImpl.java
│   └── CustomUserDetailsService.java
│
├── service
│
├── exception
│
└── NexcartApplication.java
```

---

## 🗺 Roadmap

### Phase 1

* Authentication & Authorization ✅
* Security Configuration ✅

### Phase 2

* Product Management
* Category Management
* Product Search

### Phase 3

* Shopping Cart
* Order Processing
* Inventory Management

### Phase 4

* Payment Integration
* Reviews & Ratings
* Reporting Dashboard

---

## 📄 License

This project is developed for educational, portfolio, and learning purposes.

---

## 👨‍💻 Author

Developed as part of the NexCart E-Commerce Platform using Spring Boot, Spring Security, JWT Authentication, and MySQL.
