FROM adoptopenjdk/openjdk11:jre-11.0.6_10-alpine
COPY "./target/com.meli.adn.app-0.0.1-SNAPSHOT.jar" "app.jar"
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]