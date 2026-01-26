# VetAppBackend
Master Studies Project - Veterinary Clinic Management System

![Java](https://img.shields.io/badge/Java-17-ED8B00?style=flat-square&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.2.1-6DB33F?style=flat-square&logo=spring-boot&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring_Security-JWT-6DB33F?style=flat-square&logo=spring-security&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Active-316192?style=flat-square&logo=postgresql&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-ORM-59666C?style=flat-square&logo=Hibernate&logoColor=white)

## Project Overview
This project is a backend system developed for a veterinary clinic management application. It provides a secure RESTful API that enables management of veterinarians, pets (patients), owners, medical examinations, and appointment scheduling. The system is designed with a focus on data integrity, security, and scalability.

## Core Features
* **Authentication & Authorization**: Implemented using Spring Security with JWT (JSON Web Tokens).
* **Role-Based Access Control (RBAC)**: Fine-grained permissions (e.g., `EXAMINATION_ADD`, `BREED_LIST`) to ensure secure data access.
* **Automated Documentation**: Fully integrated OpenAPI 3 (Swagger) specification for API testing and exploration.
* **Database Management**: Relational data modeling using PostgreSQL with Hibernate as the ORM provider.
* **Testing Environment**: Dedicated integration testing suite using an in-memory H2 database with PostgreSQL compatibility mode.

## Technologies
| Layer             | Technology                             |
|:------------------|:---------------------------------------|
| **Language**      | Java 17                                |
| **Framework**     | Spring Boot 3.2.1                      |
| **Security**      | Spring Security, JJWT (JSON Web Token) |
| **Persistence**   | Spring Data JPA, Hibernate             |
| **Database**      | PostgreSQL (Production), H2 (Testing)  |
| **Documentation** | SpringDoc OpenAPI 3                    |
| **Build Tool**    | Maven                                  |

## Installation and Setup

### Prerequisites
* Java Development Kit (JDK) 17 or higher
* Apache Maven 3.6 or higher
* PostgreSQL Database instance

### Configuration
1. **Database Setup**: Create a PostgreSQL database (e.g., `vet_clinic`).
2. **Properties**: Update `src/main/resources/application.properties` with your credentials:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/vet_clinic
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

### Building and Running 

This assumes that you are already running a PostgreSQL database instance.

1. Clone the repository:

```bash
git clone [https://github.com/your-username/VetAppBackend.git](https://github.com/your-username/VetAppBackend.git)
```

2. Navigate to the project root:

```bash
cd VetAppBackend
```

3. Build and run the application:
```Bash
mvn spring-boot:run
```

## Running with Docker (Optional)

The application can also be started using Docker for easier setup and environment consistency.
It comes with a postgres database preconfigured.

### Prerequisites
- Docker
- Docker Compose

### Steps

1. Build and start the containers:

```bash
docker-compose up --build
```
   
## API Documentation and Testing

The application provides an interactive Swagger UI for API exploration.

Swagger UI URL: `http://domain/api/swagger-ui/index.html`<br>
OpenAPI Docs: `http://domain/api/v3/api-docs`

### Authentication Steps:

1. Navigate to the **AuthController** section in Swagger.

2. Execute the `/auth/login` endpoint with valid credentials.

3. Copy the token from the JSON response.

4. Click the Authorize button at the top of the page.

5. Enter the token in the following format: Bearer <your_token_here>.

## System Architecture

The project follows a standard N-tier architecture to ensure separation of concerns:

* Controller Layer: REST controllers that handle HTTP requests and responses.

* Service Layer: Contains business logic and orchestrates data between controllers and repositories.

* Repository Layer: Uses Spring Data JPA interfaces for database communication.

* Domain Layer: Contains JPA entities (e.g., Examination, Patient, Veterinarian) and defines the database schema.

* DTO Layer: Data Transfer Objects used to shield the internal domain model from the API layer.

## Testing

Integration tests are located in src/test/java. These tests run in an isolated environment using an H2 database.

To run the automated test suite:
```bash
mvn test
```

The test environment configuration can be found in src/test/resources/application-test.properties.