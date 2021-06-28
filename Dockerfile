FROM openjdk:11
ADD busines-tracker-api/build/libs/busines-tracker-api.jar busines-tracker-api.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "busines-tracker-api.jar"]
