# Stage 1: Gradle cache dependencies
FROM gradle:latest AS cache
WORKDIR /home/gradle/app
COPY settings.gradle.kts build.gradle.* gradle.properties /home/gradle/app/
COPY gradle /home/gradle/app/gradle
RUN gradle dependencies --no-daemon || return 0

# Stage 2: Build Application
FROM gradle:latest AS build
WORKDIR /home/gradle/app
COPY --from=cache /home/gradle/.gradle /home/gradle/.gradle
COPY --chown=gradle:gradle . .
RUN git config --global --add safe.directory /home/gradle/app
RUN gradle :mir-service:buildFatJar --no-daemon

# Stage 3: Runtime image
FROM amazoncorretto:25 AS runtime
WORKDIR /app
EXPOSE 8080
COPY --from=build /home/gradle/app/mir-service/build/libs/mir-service.jar /app/mir-service.jar
ENTRYPOINT ["java","-jar","/app/mir-service.jar"]
