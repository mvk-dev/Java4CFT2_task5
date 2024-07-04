FROM openjdk:17-jdk-alpine
COPY "/../../target/java4cft2_task5.jar" application.jar
ENTRYPOINT ["java","-jar","/application.jar"]