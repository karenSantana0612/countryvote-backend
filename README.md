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
This service allows users to vote for their favorite country.
Each email can submit only one vote.
It also provides the top 10 most voted countries with detailed information retrieved from RestCountries API.

## How to run
(To be completed)

