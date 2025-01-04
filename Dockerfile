FROM eclipse-temurin:23.0.1_11-jdk-ubi9-minimal AS builder

WORKDIR /app

COPY . .

RUN microdnf install findutils -y && ./gradlew bootJar -DskipTests

FROM eclipse-temurin:23.0.1_11-jre-ubi9-minimal

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]