FROM openjdk:11
ADD business-tracker-api/build/libs/business-tracker-api.jar business-tracker-api.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "business-tracker-api.jar"]
