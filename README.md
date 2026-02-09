# CountryVote Backend

Servicio backend para la aplicación **CountryVote**.

Este proyecto expone una API REST que permite registrar votos por países y consultar el Top 10 de países más votados, enriqueciendo la información con datos obtenidos desde una API pública externa.

---

## Tech Stack

- Java 17
- Spring Boot
- Spring Data JPA
- Maven
- Base de datos:
  - PostgreSQL (pensado para producción)
  - H2 en memoria (usado para desarrollo y pruebas)

---

## Arquitectura

El proyecto sigue una **arquitectura en capas (N-Layer Architecture)**, con una separación clara de responsabilidades:

### 1. Controller
- Expone los endpoints REST.
- Valida entradas básicas (request params / body).
- No contiene lógica de negocio.
- Ejemplo: `CountryController`, `VoteController`, `FavoriteController`.

### 2. Service
- Contiene la lógica de negocio.
- Aplica reglas del dominio (por ejemplo: un solo voto por email).
- Orquesta llamadas a repositorios y servicios externos.
- Ejemplo: `VoteService`, `FavoriteService`, `RestCountriesProxyService`.

### 3. Repository
- Acceso a datos usando Spring Data JPA.
- Define consultas a la base de datos.
- No contiene lógica de negocio.
- Ejemplo: `UserVoteRepository`.

### 4. Domain / Entity
- Representa el modelo del dominio.
- Define las entidades JPA que se persisten en base de datos.
- Ejemplo: `UserVote`.

### 5. Integration (External APIs)
- Encapsula la comunicación con APIs externas.
- En este caso, la API pública **RestCountries**.
- Se utiliza `WebClient` para consumir servicios externos de forma reactiva.
- Permite desacoplar el dominio interno de la estructura de la API externa.

Esta arquitectura facilita:
- Mantenibilidad
- Escalabilidad
- Claridad en responsabilidades
- Facilidad para pruebas y extensiones futuras

---

## Descripción Funcional

### Reglas de negocio
- Cada correo electrónico solo puede registrar **un voto**.
- Cada voto está asociado a **un país**.
- Si un email intenta votar más de una vez, el sistema devuelve un error.

### Funcionalidades principales
- Registrar un voto (nombre, email y país).
- Obtener el **Top 10 de países más votados**.
- Para el Top 10, se enriquecen los datos del país usando la API pública RestCountries:
  - Nombre común
  - Nombre oficial
  - Capital
  - Región
  - Subregión
  - Cantidad de votos

---

## API Externa

Este proyecto consume la API pública **RestCountries**:

https://restcountries.com/

Se utiliza principalmente para:
- Listar países disponibles.
- Obtener información detallada de países al construir el Top 10.

---

## Endpoints Principales

### Countries (Proxy a RestCountries)

- Obtener todos los países  
  `GET /api/countries/all`

- Buscar país por nombre  
  `GET /api/countries/name/{name}`

- Buscar países por región  
  `GET /api/countries/region/{region}`

- Buscar país(es) por código  
  `GET /api/countries/alpha/{code}`  
  `GET /api/countries/alpha?codes=co,pe,it`

---

### Votes

- Crear un voto  
  `POST /api/votes`

  Ejemplo de body:
  ```json
  {
    "name": "Juan Perez",
    "email": "juan@gmail.com",
    "countryCode": "IT"
  }
