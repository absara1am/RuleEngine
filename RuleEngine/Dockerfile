# Use the official OpenJDK 17 image as a base image
FROM openjdk:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven project files to the working directory
COPY pom.xml ./
COPY .mvn ./.mvn
COPY mvnw ./

# Ensure the Maven wrapper has executable permissions
RUN chmod +x ./mvnw

# Download project dependencies (cached if no change in pom.xml)
RUN ./mvnw dependency:go-offline

# Copy the source code
COPY src ./src

# Package the application into a jar
RUN ./mvnw package -DskipTests

# Expose port 8080 to the outside world
EXPOSE 8080

# The command to run your application
CMD ["java", "-jar", "target/RuleEngine-0.0.1-SNAPSHOT.jar"]
