# Use an official OpenJDK image as a base
FROM openjdk:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file of the Spring Boot application into the container
COPY target/your-app-name.jar /app/app.jar

# Expose the port that the Spring Boot application will run on
EXPOSE 8089

# Command to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
