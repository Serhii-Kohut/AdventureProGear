FROM openjdk:17-jdk

WORKDIR /app

COPY src /app/src

COPY target/AdventureProGearJava-0.0.1-SNAPSHOT.jar /app/adventure.jar

EXPOSE 8080

CMD ["java", "-jar", "adventure.jar"]