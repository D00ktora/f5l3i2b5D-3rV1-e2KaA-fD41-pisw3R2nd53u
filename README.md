# Gas Stations Micro-service Documentation

## Introduction
This micro-service provides functionalities to retrieve and store current gas station data from a designated source and offers search capabilities for gas stations based on name, as well as statistical analysis of fuel prices.

## Coding Challenge Description
The objective is to create a micro-service that fetches the latest gas station data from a provided link on every start, filters and stores only open stations in a local database, and exposes REST endpoints for search functions and statistical analysis.

# Some text


### Search Functions:
- Retrieve median, maximum, and minimum prices for a given fuel type (accepted types: diesel, e5, and e10). If wrong input is provided error code 404 not find.
- Search gas stations by name. If name is invalid (not existing in DB) error code 404 not find. 

## Requirements
- **Framework:** Utilize Spring Boot for development
- **Data Storage:** Implement storage in a local database
- **RESTful API:** Create endpoints to enable interaction with the service
- **Documentation:** Include comprehensive instructions in the README.md file on building, starting, and accessing the service
- **Version Control:** Host the project on GitHub

## Implementation Approach
- **Data Retrieval:** Fetch gas station data on startup and store in a local database, filtering out closed stations.
- **REST Endpoints:** Develop endpoints for searching gas stations by name or retrieving statistical data for fuel prices.
- **Technologies Used:** Spring Boot, Database (MySQL), RESTful APIs.

## Usage Instructions
1. **Clone Repository:** Obtain the project from the GitHub repository.
2. **Database Configuration:** Configure database connection settings in the application properties file.
3. **Build Project:** Compile the project using Gradle.
4. **Run Application:** Start the Spring Boot application.
5. **API Access:** Interact with the service using the provided REST endpoints.

## Available Endpoints
- `/gas-stations/{name}` : Search gas stations by name.
- `/fuel-prices/{fuelType}` : Retrieve statistical data (median, maximum, minimum prices) for a given fuel type.

## GitHub Repository
Access the project repository on GitHub: [https://github.com/D00ktora/f5l3i2b5D-3rV1-e2KaA-fD41-pisw3R2nd53u](https://github.com/D00ktora/f5l3i2b5D-3rV1-e2KaA-fD41-pisw3R2nd53u)

Feel free to explore and utilize our Gas Stations Micro-service for your needs!
