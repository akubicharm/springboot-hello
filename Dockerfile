# builder - first stage to build the application
FROM maven:3-openjdk-17-slim AS build-env
ADD ./pom.xml pom.xml
RUN mvn dependency:go-offline
ADD ./src src/
RUN mvn -e clean package

# runtime - build final runtime image
FROM openjdk:17.0.2-slim

COPY --from=build-env target/rest-service-complete-0.0.1-SNAPSHOT.jar app.jar

# run application
EXPOSE 8080

ENTRYPOINT ["java", "-jar","/app.jar"]
