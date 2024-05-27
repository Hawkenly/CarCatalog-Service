FROM openjdk:17-jdk-slim-buster
WORKDIR /app
COPY /target/CarCatalog-0.0.1-SNAPSHOT.jar /app/CarCatalog.jar
ENTRYPOINT ["java", "-jar", "CarCatalog.jar"]