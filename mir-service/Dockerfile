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
RUN gradle :mir-service:buildFatJar --no-daemon

# Stage 3: Create the Runtime Image
FROM amazoncorretto:24 AS runtime
EXPOSE 8080
RUN mkdir /app
COPY --from=build /home/gradle/mir-service/build/libs/mir-service.jar /app/mir-service.jar
ENTRYPOINT ["java","-jar","/app/mir-service.jar"]