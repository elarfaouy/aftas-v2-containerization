# this stage for packaging our app into a jar file
FROM maven:3.9.6-ibm-semeru-21-jammy AS package

WORKDIR /app

COPY pom.xml .

COPY src src

RUN mvn clean package  -DskipTests

# second stage to run our app
FROM openjdk:21-jdk-oracle

WORKDIR /app

COPY --from=package /app/target/aftas-0.0.2.jar .

EXPOSE 8888

CMD ["java", "-jar", "aftas-0.0.2.jar"]
