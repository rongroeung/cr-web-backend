# define environment (parent image) to use for our application (child image)
FROM eclipse-temurin:8-jdk-jammy
# copy the packaged jar file into our docker image
COPY CR_Web_Backend-1.0.0-SNAPSHOT.jar app.jar
# set the startup command to execute the jar
CMD java -jar app.jar