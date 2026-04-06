FROM maven:3.9.6-eclipse-temurin-21-jammy AS builder
WORKDIR /app
COPY pom.xml .
# Tải dependencies trước để cache
RUN mvn dependency:go-offline || true
COPY src ./src
# Build file jar
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080
# Chạy Spring Boot với profile prod
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "app.jar"]
