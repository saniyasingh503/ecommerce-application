# Use a Java base image
FROM openjdk:17-jdk-alpine

# Set the working directory to /app
WORKDIR /app

# Copy the Spring Boot application JAR file into the Docker image
COPY target/api-gateway-service-0.0.1-SNAPSHOT.jar /app/api-gateway-service-0.0.1-SNAPSHOT.jar

# Set environment variables
ENV SERVER_PORT=8083
ENV SPRING_DATASOURCE_URL=jdbc:h2:mem:testdb
ENV SPRING_DATASOURCE_USERNAME=sa
ENV SPRING_DATASOURCE_PASSWORD=password

# Expose the port that the Spring Boot application is listening on
EXPOSE 8083

# Run the Spring Boot application when the container starts
CMD ["java", "-jar", "api-gateway-service-0.0.1-SNAPSHOT.jar"]