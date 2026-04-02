# Base image: eclipse-temurin:17-jdk-alpine
FROM eclipse-temurin:17-jdk-alpine

# Set working directory
WORKDIR /app

# Copy mvnw and pom.xml first for layer caching
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Grant execute permission for the mvnw script
RUN chmod +x mvnw

# Resolve dependencies (layer caching)
RUN ./mvnw dependency:go-offline -B

# Copy the source code
COPY src ./src

# Build the application
RUN ./mvnw package -DskipTests

# Expose port 8080
EXPOSE 8080

# Command to run the application
CMD ["java", "-jar", "target/Thymeleaf-Demo-0.0.1-SNAPSHOT.jar"]
