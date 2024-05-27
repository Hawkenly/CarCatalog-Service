FROM openjdk:17-jdk-slim-buster as builder
WORKDIR /app
COPY --from=builder target/*.jar app.jar
COPY ./init.sql /docker-entrypoint-initdb.d/
ENTRYPOINT ["java", "-jar", "carcatalog.jar"]