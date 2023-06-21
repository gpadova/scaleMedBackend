# ScaleMed Backend

Welcome to the ScaleMed Backend repository! This is the backend component of the ScaleMed App, a ToDo application that allows users to manage their tasks efficiently. The backend is built using Quarkus and interacts with a PostgreSQL database.

## Prerequisites

Before getting started with the ScaleMed Backend, ensure you have the following prerequisites installed on your system:

- Java Development Kit (JDK) 11 or later
- Apache Maven
- PostgreSQL database

## Installation

To install and run the ScaleMed Backend, follow these steps:

__1. Clone the repository:__
 ```git clone https://github.com/gpadova/scaleMedBackend.git```

__2. Navigate to the project directory:__
```cd scaleMedBackend```

__3. Run you project:__

 ```./mvnw quarkus:dev```

__4. The ScaleMed Backend should now be running on `http://localhost:8080`.__

## API Documentation

The ScaleMed Backend provides the following API endpoints:

- `POST /tasks`: Create a new task sent with proper body.
   The body consists of a object like this:

   ```
    {
        title: String,
        description: String,
        dateOfCompletion: LocalDate
    }
- `GET /tasks`: Get all tasks.
- `PATCH /tasks/{id}`: Update a specific task by ID sending in the body the info wanted to be changed.

- `DELETE /tasks/{id}`: Delete a specific task by ID.

## Configuration

The configuration for the ScaleMed Backend is located in the `src/main/resources/application.properties` file. You can modify this file to adjust various settings, including the database connection, logging, and other application-specific configurations.
