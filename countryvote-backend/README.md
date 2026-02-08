# CountryVote Backend

Backend service for the CountryVote application.

## Tech Stack
- Java 17
- Spring Boot
- Spring Data JPA
- PostgreSQL (H2 for dev/test)
- Maven

## Architecture
N-layer architecture:
- Controller
- Service
- Repository
- Domain / Entity
- Integration (external APIs)

## Description
Rules:
- Each email can submit only one vote.
- A vote is associated with one country.

The service also provides a list of the top 10 most voted countries.
For each country, detailed information is retrieved from the public RestCountries API, such as:
- country name
- official name
- capital
- region
- subregion

## External API

This project consumes the public RestCountries API:

https://restcountries.com/

The API is used to enrich country data when listing favorites.

## Main Endpoints

### Countries (proxy to RestCountries)

- Get all countries
GET /api/countries/all
- Get country by name
GET /api/countries/name/{name}
- Get countries by region GET /api/countries/region/{region} 
- Get country or countries by code  
  GET /api/countries/alpha/{code}
  GET /api/countries/alpha?codes=co,pe,it

### Votes
- Create a vote  
  POST /api/votes
  Body example:
```json
{
  "name": "Juan Perez",
  "email": "juan@gmail.com",
  "countryCode": "IT"
}

If the email already exists, the service returns an error indicating that only one vote per email is allowed.
Favorites
	•	Get top 10 favorite countries
GET /api/favorites/top10
This endpoint combines vote data from the database with country details from RestCountries API.

Database
	•	H2 in-memory database is used for development and testing.
	•	Tables are created automatically at application startup.
	•	A unique constraint is defined on the email field to ensure only one vote per user.

H2 Console: http://localhost:8080/h2-console
jdbc:h2:mem:countryvote

User: sa
Password: (empty)

How to run
	1.	Clone the repository
	2.	Run the application using your IDE or with:
mvn spring-boot:run

	3.	Use Postman or a similar tool to test the endpoints