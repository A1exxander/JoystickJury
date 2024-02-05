FROM openjdk:11-jre-slim

LABEL maintainer="raposoalexander@gmail.com"

RUN mkdir -p /app

WORKDIR /app

COPY target/backend-0.0.1-SNAPSHOT-jar-with-dependencies.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]


