FROM gradle:8.5.0-jdk21 AS build

WORKDIR /app

COPY . .

RUN gradle bootJar

FROM openjdk:21-jdk-slim

WORKDIR /app

COPY --from=build app/build/libs/*.jar /app/app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]