FROM openjdk:11
ADD busines-tracker-api/build/libs/*.jar busines-tracker.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "busines-tracker.jar"]
