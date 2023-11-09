FROM openjdk:11-jre-slim

COPY target/tu-aplicacion.jar /app.jar

EXPOSE 8080

CMD ["java", "-jar", "/app.jar"]
