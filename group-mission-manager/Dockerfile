#FROM gradle:8.7-jdk17 AS build
#WORKDIR /app
#COPY . .
#RUN gradle :group-mission-manager:build --no-daemon
#
#FROM openjdk:17-slim
#WORKDIR /app
#COPY --from=build /app/group-mission-manager/build/libs/*.jar /app/app.jar
#EXPOSE 8080
#ENTRYPOINT ["java", "-jar", "/app/app.jar"]

# Stage 1: Cache Gradle dependencies
FROM gradle:latest AS cache
RUN mkdir -p /home/gradle/cache_home
ENV GRADLE_USER_HOME=/home/gradle/cache_home
COPY build.gradle.* gradle.properties /home/gradle/app/
COPY gradle /home/gradle/app/gradle
WORKDIR /home/gradle/app
RUN gradle clean build -i --stacktrace

# Stage 2: Build Application
FROM gradle:latest AS build
COPY --from=cache /home/gradle/cache_home /home/gradle/.gradle
COPY --chown=gradle:gradle . /home/gradle/
WORKDIR /home/gradle
# Build the fat JAR, Gradle also supports shadow
# and boot JAR by default.
RUN gradle :group-mission-manager:buildFatJar --no-daemon

# Stage 3: Create the Runtime Image
FROM amazoncorretto:22 AS runtime
EXPOSE 8080
RUN mkdir /app
COPY --from=build /home/gradle/group-mission-manager/build/libs/group-mission-manager.jar /app/group-mission-manager.jar
ENTRYPOINT ["java","-jar","/app/group-mission-manager.jar"]