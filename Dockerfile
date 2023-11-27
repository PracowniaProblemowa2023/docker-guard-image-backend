FROM maven:3.8.5-openjdk-17-slim AS builder

WORKDIR /build
COPY . .
RUN mvn clean package -Dmaven.compiler.source=17 -Dmaven.compiler.target=17 -DskipTests



FROM eclipse-temurin:17-jdk-focal

RUN apt-get update -y && \
    curl -sSfL https://raw.githubusercontent.com/anchore/syft/main/install.sh | sh -s -- -b /usr/local/bin && \
    useradd --home /apps --shell /bin/bash --uid 1001 spring
COPY --from=builder /build/api/target/api-0.0.1-SNAPSHOT.jar  /apps/app.jar
RUN chmod -R 755 /apps && \
    chown -R spring:spring /apps

USER spring
WORKDIR /apps

ENTRYPOINT ["java", "-jar", "./app.jar"]