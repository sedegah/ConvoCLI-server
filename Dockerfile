FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY . .
RUN ./mvnw clean package -DskipTests
CMD ["java","-jar","target/server-0.1.0.jar"]
