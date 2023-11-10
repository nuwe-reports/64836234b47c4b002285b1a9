# Imagen base para MySQL
FROM mysql:8

ENV MYSQL_ROOT_PASSWORD=root
ENV MYSQL_DATABASE=accwe-hospital
ENV MYSQL_USER=root
ENV MYSQL_PASSWORD=root

COPY init.sql /docker-entrypoint-initdb.d/

# Dockerfile.maven
FROM maven:3.8.1-openjdk-11 AS builder

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline

COPY src ./src

RUN mvn test

RUN mvn package

FROM openjdk:11-jre-slim

WORKDIR /app

COPY --from=builder /app/target/accenture-techhub-0.0.1-SNAPSHOT.war /app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
