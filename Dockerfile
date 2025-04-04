FROM openjdk:21
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 9990
ENTRYPOINT ["java", "-jar", "app.jar"]