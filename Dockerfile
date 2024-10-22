# Use the official OpenJDK base image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the Maven build file and the source code into the container
COPY pom.xml .
COPY src ./src

# Build the application
RUN ./mvnw clean package -DskipTests

# Copy the built JAR file to a specific location
COPY target/*.jar app.jar

# Copy the application.properties file into the container
COPY src/main/resources/application.properties ./application.properties

# Expose the port on which the application will run
EXPOSE 8089

# Command to run the application with the specified properties file
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.config.location=classpath:/application.properties"]
