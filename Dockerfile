FROM maven:3.8.4-openjdk-17 as build

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn package

FROM openjdk:17-jdk-slim

COPY --from=build /app/target/carcatalog-0.0.1-SNAPSHOT.jar /app/carcatalog.jar

ENTRYPOINT ["java", "-jar", "/app/carcatalog.jar"]