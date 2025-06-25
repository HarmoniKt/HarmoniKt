FROM gradle:8.7-jdk17 AS build
WORKDIR /app
COPY . .
RUN gradle :spot-service:build --no-daemon

FROM openjdk:17-slim
WORKDIR /app
COPY --from=build /app/spot-service/build/libs/*.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]