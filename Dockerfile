FROM openjdk:17-jdk-alpine

# Install dockerize
RUN apk add --no-cache wget \
    && wget https://github.com/jwilder/dockerize/releases/download/v0.6.1/dockerize-linux-amd64-v0.6.1.tar.gz \
    && tar -C /usr/local/bin -xzvf dockerize-linux-amd64-v0.6.1.tar.gz \
    && rm dockerize-linux-amd64-v0.6.1.tar.gz

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file of the Spring Boot application into the container
COPY target/tamimhmizi_5artic5_g3_stationski.jar /app/app.jar

# Expose the port that the Spring Boot application will run on
EXPOSE 8089

# Command to run the Spring Boot application, using dockerize to wait for MySQL
ENTRYPOINT ["dockerize", "-wait", "tcp://mysql-ski-station:3306", "-timeout", "60s", "java", "-jar", "/app/app.jar"]
