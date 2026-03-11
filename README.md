# URL Shortening Service

A RESTful URL Shortening Service built with Spring Boot.
The application generates short links for long URLs and redirects users to the original URL when the short link is accessed.

This project demonstrates backend development concepts such as REST APIs, database persistence, caching, and containerized deployment.

---

## Features

* Generate a short URL for a long URL
* Redirect users to the original URL
* URL expiration support
* Database persistence using H2
* Redis caching for faster lookups
* REST API architecture
* Docker-ready application

---

## Tech Stack

Backend

* Java
* Spring Boot
* Spring Data JPA

Database

* H2 Database (development)
* PostgreSQL(now)
* Redis
---


## API Endpoints

### Generate Short URL

POST /generate

Example request:

```
{
  "url": "https://www.google.com",
  "expirationDate": "2026-03-10T12:00:00"
}
```

Example response:

```
{
  "originalUrl": "https://www.google.com",
  "urlShortLink": "abc123",
  "expirationDate": "2026-03-10T12:00:00"
}
```

---

### Redirect to Original URL

GET /{shortLink}

Example:

```
GET /abc123
```

Response: HTTP Redirect → original URL

---

## Project Structure

```
src
 └── main
     ├── controller
     │   └── UrlShorteningController
     ├── service
     │   └── UrlServiceImpl
     ├── repository
     │   └── UrlRepository
     ├── model
     │   ├── Url
     │   ├── UrlDto
     │   ├── UrlResponseDto
     │   └── UrlErrorResponseDto
     └── UrlShorteningServiceApplication
```

---

### 1. Clone the repository

```
git clone https://github.com/yourusername/url-shortener.git
```

### 2. Run the application

```
mvn spring-boot:run
```

Application will run on:

```
http://localhost:8080
```

---

## Future Improvements

* Replace H2 with PostgreSQL :✅done
* Improve short link generation using Base62 encoding
* Add Redis caching layer: ✅done
* Add Docker containerization :✅done
* Deploy with Kubernetes
* Add GitHub Actions CI/CD pipeline
* Add analytics for link usage
* ⚠️⚠️⚠️Note: Networking between containers may still need troubleshooting. This setup is experimental but a great learning experience for Spring, PostgreSQL, Redis, and Docker.
---

## Learning Goals

This project was built to practice:

* REST API development
* Spring Boot architecture
* Database persistence
* Caching strategies
* Containerization
* DevOps workflows

---

## Author

Built as a backend learning project using Java and Spring Boot.
