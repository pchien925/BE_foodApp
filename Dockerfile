FROM openjdk:21
WORKDIR /app
COPY target/foodApp-1.0.jar app.jar
EXPOSE 9990
ENTRYPOINT ["java", "-jar", "app.jar"]