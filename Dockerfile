FROM eclipse-temurin:21-jdk-alpine

# Set working directory in container
WORKDIR /app

# Install bash for wait-for-it
RUN apk add --no-cache bash

# Copy Spring Boot jar
COPY target/Url_Shortening_Service-0.0.1-SNAPSHOT.jar app.jar

# Copy wait-for-it script
COPY wait-for-it.sh /app/wait-for-it.sh
RUN chmod +x /app/wait-for-it.sh

# Expose Spring Boot port
EXPOSE 8080

# Start app after Postgres is ready
CMD ["./wait-for-it.sh", "postgres:5432", "--timeout=60", "--strict", "--", "java", "-jar", "app.jar"]