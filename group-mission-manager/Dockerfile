FROM gradle:8.7-jdk17 AS build
WORKDIR /app
COPY . .
RUN gradle :group-mission-manager:build --no-daemon

FROM openjdk:17-slim
WORKDIR /app
COPY --from=build /app/group-mission-manager/build/libs/*.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]