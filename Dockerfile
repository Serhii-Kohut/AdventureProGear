FROM maven:3.9.7 AS build
WORKDIR /app
COPY pom.xml /app
COPY uploads/ /app/uploads/
RUN mvn dependency:resolve
COPY . /app
RUN mvn clean
RUN mvn package -DskipTests -X

FROM openjdk:17-alpine
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]