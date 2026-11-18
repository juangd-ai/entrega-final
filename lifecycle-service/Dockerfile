# ---------- Etapa 1: compilar el JAR dentro de Docker (no necesitas Maven instalado) ----------
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /build
COPY pom.xml .
COPY src ./src
RUN mvn -B clean package -DskipTests

# ---------- Etapa 2: imagen liviana de ejecución ----------
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /build/target/app.jar app.jar
EXPOSE 8084
ENTRYPOINT ["java", "-jar", "app.jar"]
