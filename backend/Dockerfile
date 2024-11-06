FROM openjdk:17-jdk-slim

RUN adduser --system spring-boot && addgroup --system spring-boot && adduser spring-boot spring-boot

USER spring-boot

WORKDIR /app

COPY target/backend-0.0.1-SNAPSHOT.jar /app/backend.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/backend.jar"]
