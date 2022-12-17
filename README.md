# TestyCool Server

REST API Server Application for TestyCool online exam suite. Developed using Spring Boot 2.7.6

## Development

### Requirement

- JDK 11+
- Maven
- PostgreSQL, either install on system or run through docker container using included [docker-compose.yml](docker-compose.yml)

### How To Run

- Clone/Download and Import project into your IDE
- Run the app by either running, `./mvnw spring-boot:run` or compile [TestyCoolServerApplication.java](src/main/java/com/testycool/testycoolserver/TestycoolServerApplication.java)
- the API could be accessed through base url `http://localhost:50051`

### Environment Variables

Some Environment Variables that could be configured

`POSTGRES_HOST` default = localhost

`POSTGRES_PORT` default = 5432

`POSTGRES_DB` default = testycool

`POSTGRES_USER` default = postgres

`POSTGRES_USER_PASSWORD` default = postgres

`SERVER_PORT` optional, default = 50051

`JWT_EXPIRATION` optional, default = 300000

## Project Structure


## API Documentation

All available API endpoints could be viewed using swagger-ui by accessing `http://localhost:50051/swagger-ui/index.html`
