FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY . .
RUN mvn clean package -DskipTests

# Etapa 2: Imagem final com a aplicação
FROM eclipse-temurin:21-jdk
VOLUME /tmp
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]