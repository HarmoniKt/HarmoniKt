# Stage 1: Gradle cache dependencies
FROM gradle:8.14.3-jdk24-corretto AS cache
WORKDIR /home/gradle/app
COPY settings.gradle.kts build.gradle.* gradle.properties /home/gradle/app/
COPY gradle /home/gradle/app/gradle
RUN gradle dependencies --no-daemon || true

# Stage 2: Build Application
FROM gradle:8.14.3-jdk24-corretto AS build
WORKDIR /home/gradle/app
COPY --from=cache /home/gradle/.gradle /home/gradle/.gradle
COPY --chown=gradle:gradle . .
RUN git config --global --add safe.directory /home/gradle/app
RUN gradle :robot-manager:buildFatJar --no-daemon

# Stage 3: Create the Runtime Image
FROM amazoncorretto:25 AS runtime
EXPOSE 8080
RUN mkdir /app
COPY --from=build /home/gradle/app/robot-manager/build/libs/robot-manager.jar /app/robot-manager.jar
ENTRYPOINT ["java","-jar","/app/robot-manager.jar"]