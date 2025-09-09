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
RUN gradle :map-manager:buildFatJar --no-daemon

# Stage 3: Create the Runtime Image
FROM amazoncorretto:24 AS runtime
EXPOSE 8080
RUN mkdir /app
COPY --from=build /home/gradle/app/map-manager/build/libs/map-manager.jar /app/map-manager.jar
ENTRYPOINT ["java","-jar","/app/map-manager.jar"]