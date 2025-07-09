FROM gradle:8.14.3-jdk21-alpine AS builder

USER root
WORKDIR /app

COPY build.gradle.kts settings.gradle.kts ./
COPY gradle ./gradle

RUN gradle dependencies

COPY src ./src
RUN gradle bootJar

FROM eclipse-temurin:21-alpine AS runtime

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -Djava.security.egd=file:/dev/./urandom"
EXPOSE 8080

ENTRYPOINT exec java $JAVA_OPTS -jar app.jar