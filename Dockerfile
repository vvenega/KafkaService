FROM adoptopenjdk/openjdk8
WORKDIR /
ARG KafkaService-0.0.1-SNAPSHOT.jar
ADD KafkaService-0.0.1-SNAPSHOT.jar /app.jar
EXPOSE 8002
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]