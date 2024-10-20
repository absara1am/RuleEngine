# Use the official OpenJDK 17 image as a base image
FROM openjdk:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven project files to the working directory
COPY pom.xml ./
COPY src ./src

# Copy the Maven wrapper if available
COPY mvnw ./
COPY .mvn ./.mvn

# Install Maven dependencies and build the application
RUN ./mvnw install

# Package the application into a jar
RUN ./mvnw package -DskipTests

# Expose port 8080 to the outside world
EXPOSE 8080

# The command to run your application
CMD ["java", "-jar", "target/RuleEngine-0.0.1-SNAPSHOT.jar"]
