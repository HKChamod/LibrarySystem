# Fullstack Library Management System

A production-ready Library Management System built with Java (Spring Boot) and React (Vite).

## Tech Stack
- **Backend**: Spring Boot, Spring Security (JWT), Spring Data JPA, Hibernate
- **Database**: MySQL 8.0
- **Frontend**: React 18, TailwindCSS, Axios, Vite
- **Deployment**: Docker Compose

## Features
- **User Authentication**: JWT-based Login/Signup (Admin/Librarian/User roles).
- **Books Management**: Add, Edit, Delete, Search (Title/Author/Category).
- **Borrowing System**: Issue books, Return books, Calculate fines.
- **Admin Dashboard**: Manage books and view issued books status.

## Prerequisites
- Docker & Docker Compose

## Setup & Run

1. **Clone the repository** (if applicable).
2. **Navigate to project root**:
   ```sh
   cd LibrarySystem
   ```
3. **Build and Run with Docker Compose**:
   ```sh
   docker-compose up --build
   ```
   This will:
   - Start MySQL on port 3307 (mapped from container 3306).
   - Build and start Backend on port 8080.
   - Build and start Frontend on port 5173 (or 80 mapped to 5173/3000 depending on config). *Note: Dockerfile exposes 80, compose maps 5173->80*.

4. **Access the Application**:
   - Frontend: [http://localhost:5173](http://localhost:5173)
   - Backend API: [http://localhost:8080/api](http://localhost:8080/api)
   - Swagger Documentation: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## Default Roles (Created on Startup)
- `ROLE_USER`
- `ROLE_LIBRARIAN`
- `ROLE_ADMIN`

## Seed Data
- You can register a new user via the Signup page.
- You can manually insert an Admin user into the database if needed, or modify `DataInitializer.java` to create a default admin.

## API Endpoints
- `POST /api/auth/signin` - Login
- `POST /api/auth/signup` - Register
- `GET /api/books` - List books
- `POST /api/books` - Add book (Admin/Librarian)
- `POST /api/borrows/issue` - Issue book
- `POST /api/borrows/return/{id}` - Return book

## Project Structure
```
LibrarySystem/
├── backend/            # Spring Boot Application
│   ├── src/
│   ├── Dockerfile
│   └── pom.xml
├── frontend/           # React Application
│   ├── src/
│   ├── Dockerfile
│   └── package.json
└── docker-compose.yml
```
