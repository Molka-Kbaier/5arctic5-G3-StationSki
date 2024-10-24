FROM openjdk:8-jdk-alpine
EXPOSE 8089
ADD target/stationSki-1.0.jar stationSki.jar
ENTRYPOINT ["java", "-jar", "stationSki.jar"]
