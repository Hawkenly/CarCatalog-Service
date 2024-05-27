FROM maven:3.9.2-eclipse-temurin-17-alpine as builder

COPY . .

FROM eclipse-temurin:17-jre-alpine

COPY --from=builder target/*.jar app.jar
COPY ./init.sql /docker-entrypoint-initdb.d/

EXPOSE 8080

CMD ["java","-jar","app.jar"]
