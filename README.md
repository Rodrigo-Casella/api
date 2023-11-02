# Spring Boot Project README

This is a Spring Boot project with the following configuration:

- Spring Boot version: 2.1.5
- Java version: 17
- Database: MariaDB (via `mariadb-java-client`)
- Spring Boot Starters used: Data JPA, Web, Validation

## Prerequisites

Before you begin, ensure you have met the following requirements:

- Java 17+ is installed on your system.
- MariaDB or a similar database is set up and running.

## Getting Started

To get this project up and running, follow these steps:

1. Navigate to the project directory:
    ```bash
    cd api
    ```
2. Build the project using Maven:

    ```bash
    mvm clean package
    ```
3. Run the Spring Boot application:
    ```bash
    java -jar target/api-0.0.1-SNAPSHOT.jar
    ```
This will start the Spring Boot application on http://localhost:8080.

Access the API using your web browser or a tool like Postman.

## Database Configuration
This project is configured to use MariaDB. You can change the database settings in the application.properties file located in the src/main/resources directory.

## API Endpoints
1. **POST /importOpenData**

    This endpoint is used to import open data about restaurants and save it to the database. It expects a JSON payload containing restaurant data in the format specified by the RistorantiBottegheXAll class. It filters out invalid restaurant entries and saves the valid ones to the database.

    **Example:**
    ```bash
    curl -X POST -H "Content-Type: application/json" -d @ristorantiebottegheXall.json http://localhost:8080/api/v1/importOpenData
    ```
2. **GET /restaurants/{id}**
    
    Retrieve restaurant information by its unique identifier (ID). If a restaurant with the specified ID exists in the database, it returns the restaurant details. If the restaurant is not found, it returns a 404 Not Found error.

    **Example:**
    ```bash
    curl http://localhost:8080/api/v1/restaurants/3079
    ```
3. **GET /restaurants (with provincia parameter)**
    
    Retrieve a list of restaurants based on a specified "provincia" (province) parameter. It returns a list of restaurants that match the specified province. The parameter is case-insensitive for a more flexible search.

    **Example:**
    ```bash
    curl http://localhost:8080/api/v1/restaurants?provincia=Grosseto
    ```
4. **GET /restaurants (with provincia, userLat, and userLon parameters)**
    
    Retrieve a list of restaurants in a specified province, sorted by their proximity to the user's location. It takes the "provincia" (province), "userLat" (user's latitude), and "userLon" (user's longitude) parameters. The endpoint calculates the distance from the user's location to each restaurant and sorts the list by distance in ascending order.

    **Example:**
    ```bash
    curl http://localhost:8080/api/v1/restaurants?provincia=Grosseto&userLat=0&userLon=0
    ```
5. **POST /restaurants**
    
    Create a new restaurant entry by sending a JSON payload with the restaurant details. The restaurant is then saved to the database, and the newly created restaurant is returned in the response.
6. **PUT /restaurants/{id}**

    Update an existing restaurant entry by specifying the restaurant's unique identifier (ID). This endpoint expects a JSON payload with updated restaurant details. If the restaurant with the given ID is found in the database, it updates the existing entry with the new data and returns the updated restaurant. If the restaurant is not found, it returns a 404 Not Found error.
7. **DELETE /restaurants/{id}**

    Delete a restaurant entry by specifying its unique identifier (ID). If the restaurant with the specified ID exists in the database, it is removed. No response body is returned for this endpoint.