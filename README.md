# Food App

## Overview
Food App is a web application designed for browsing menus, placing food orders, and managing orders. It integrates JWT-based authentication and Spring Security for secure API access. The application is containerized using Docker for seamless deployment.

## Technologies Used
- **Backend**: Spring Boot, Spring Security, JWT
- **Database**: PostgreSQL or MySQL (configurable)
- **Containerization**: Docker, Docker Compose
- **Language**: Java
- **Build Tool**: Maven
- **API Documentation**: Swagger/OpenAPI

## Project Structure
````
food-app/
├── src/
│   ├── main/
│   │   ├── java/vn/edu/hcmute/foodapp/
│   │   │   ├── config/          # Spring Security and JWT configurations
│   │   │   ├── controller/      # API endpoints
│   │   │   ├── dto/             # Data Transfer Object classes
│   │   │   ├── entity/          # Entity classes
│   │   │   ├── exception/       # Custom exception handling
│   │   │   ├── mapper/          # Object mapping logic
│   │   │   ├── repository/      # Data access layer
│   │   │   ├── service/         # Business logic
│   │   │   ├── util/            # Utility classes
│   │   │   └── FoodAppApplication.java  # Main application class
│   │   └── resources/
│   │       ├── static/          # Static resources (e.g., CSS, JS)
│   │       ├── templates/       # HTML templates (if any)
│   │       ├── application.yml  # Default application configuration
│   │       ├── application-dev.yml   # Development environment config
│   │       ├── application-prod.yml  # Production environment config
│   │       └── application-test.yml  # Test environment config
│   ├── test/                    # Test directory
├── Dockerfile # Docker configuration
├── initdb.sql            # SQL script for initializing the database
├── docker-compose.yml           # Docker Compose configuration
├── pom.xml / build.gradle       # Maven/Gradle build file
└── README.md                    # Project documentation

````
## Getting Started
### Prerequisites
- Java: Version 21 or higher
- Docker: Latest version
- Docker Compose: Latest version
- Database: PostgreSQL or MySQL
- Build Tool: Maven or Gradle

1. Set up your development environment with Java and Docker.
    clone the repository:
    ```bash
   git clone https://github.com/your-username/food-app.git
    cd food-app
   
2. Configure Environment:
- Copy the example configuration file (if available, or create one):
    ```bash
    cp src/main/resources/application.yml src/main/resources/application.yml
- Update application.yml (or application-dev.yml) with your database credentials and JWT secret.

3.Build the application:
    ```bash
    mvn clean install
     ```
    
4Run the application:
    ```bash
    mvn spring-boot:run
    ```
 