FROM openjdk:17-jdk-slim-buster
WORKDIR /app
COPY /target/carcatalog-0.0.1-SNAPSHOT.jar /app/carcatalog.jar
COPY ./init.sql /docker-entrypoint-initdb.d/
ENTRYPOINT ["java", "-jar", "carcatalog.jar"]