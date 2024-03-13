# Student Demo Application

The Student Demo Application is a basic Student Management System designed to provide fundamental CRUD (Create, Read, Update, Delete) functionality. Developed with Spring Boot, this project serves as a foundational step in a series of demo applications. Each iteration of the project introduces additional complexity and features, offering an incremental learning curve for those interested in mastering Spring Boot for scalable applications.

The project is organized into four main branches, each representing a different version of the application with varying levels of complexity:

- **Version 1 (v1)**: Features one table, manual testing, and utilizes an in-memory database.
- **Version 2 (v2)**: Also features one table but includes automated tests (limited to 2xx HttpStatus Code tests only) and utilizes an in-memory db.
- **Version 3 (v3)**: Continues with one table but includes comprehensive automated tests and utilizes an in-memory database.
- **Version 4 (v4)**: Represents the most advanced version with three tables, comprehensive automated tests, and utilizes an in-memory database.

## Project Difficulty & Detail (v3)

1 table, full testing, in memory db

| Difficulty: | Intermediate                     |
| ----------- | -------------------------------- |
| Model(s):   | Student                          |
| Testing:    | JUnit/Mockito, Cucumber, POSTMAN |
| Database:   | H2                               |

## Getting Started

### Prerequisites

- Git
- Java 1.8
- Maven

### Cloning & Running the App

1.  Open your terminal or command prompt.

2.  Clone the repository using Git:

    ```bash
    git clone https://github.com/arsy786/student-demo.git
    ```

3.  Navigate to the cloned repository's root directory:

    ```bash
    cd student-demo
    ```

4.  Run the following Maven command to build and start the service:

    ```bash
    # For Maven
    mvn spring-boot:run

    # For Maven Wrapper
    ./mvnw spring-boot:run
    ```

The application should now be running on `localhost:8080`.

### Using the API

The REST API is documented with Swagger. After starting the application, access the Swagger UI to interact with the API at:

```bash
http://localhost:8080/swagger-ui/index.html
```
