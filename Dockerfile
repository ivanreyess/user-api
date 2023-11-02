FROM openjdk:17
VOLUME /tmp
EXPOSE 8080
ADD /target/user-api-0.0.1-SNAPSHOT.jar user-service.jar
ENTRYPOINT ["java", "-jar", "user-service.jar"]
