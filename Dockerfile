FROM maven:3.8.7-amazoncorretto-17 as build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn package -DskipTests

FROM amazoncorretto:17
ENV PROFILE=dev
WORKDIR /tmp
COPY --from=build /app/target/*.jar app.jar
CMD ["java","-Dspring.profiles.active=${PROFILE}", "-jar", "app.jar"]