FROM maven:3.8.4-openjdk-17 as build

WORKDIR /app

COPY pom.xml .
COPY src ./src
RUN mvn package -DskipTests

RUN mvn dependency:purge-local-repository -DreResolve=false

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=build /app/target/carcatalog-0.0.1-SNAPSHOT.jar /app/carcatalog.jar

ENTRYPOINT ["java", "-jar", "/app/carcatalog.jar"]