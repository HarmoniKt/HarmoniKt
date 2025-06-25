FROM gradle:8.7-jdk17 AS build
WORKDIR /app
COPY . .
RUN gradle :robot-manager:build --no-daemon

FROM openjdk:17-slim
WORKDIR /app
COPY --from=build /app/robot-manager/build/libs/*.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]