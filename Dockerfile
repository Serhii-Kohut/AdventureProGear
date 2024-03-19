FROM openjdk:17-jdk

WORKDIR /app

COPY target/AdventureProGearJava-0.0.1-SNAPSHOT.jar /app/pro.jar

EXPOSE 8080

CMD ["java", "-jar", "pro.jar"]
