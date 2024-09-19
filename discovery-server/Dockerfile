# Use a Java base image
FROM openjdk:17-jdk-alpine

# Set the working directory to /app
WORKDIR /app

# Copy the Spring Boot application JAR file into the Docker image
COPY target/discovery-server-0.0.1-SNAPSHOT.jar /app/discovery-server-0.0.1-SNAPSHOT.jar

# Expose the port that the Spring Boot application is listening on
EXPOSE 8761

# Run the Spring Boot application when the container starts
CMD ["java", "-jar", "discovery-server-0.0.1-SNAPSHOT.jar"]