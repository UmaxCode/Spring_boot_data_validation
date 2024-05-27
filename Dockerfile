FROM openjdk:17-jdk-slim AS builder
WORKDIR /app

# Copy the Maven wrapper files and project files
COPY .mvn .mvn
COPY mvnw .

# Copy the project files into the container
COPY pom.xml .

# Grant maven wrapper executable privilages
RUN chmod +x mvnw

#Download and install any dependencies specified in pom.xml
RUN ./mvnw dependency:go-offline

# Copy the application source code into the container
COPY src src

# Build the application
RUN ./mvnw package -Dmaven.test.skip

# end of building stage


# start runtime stage for creating the image
FROM openjdk:17

# Copy build artifact
COPY --from=builder /app/target/*.jar /data-validation-app.jar

#Expose port 8080
EXPOSE 8080

# Specify the command to run on container start
ENTRYPOINT ["java", "-jar", "/data-validation-app.jar"]