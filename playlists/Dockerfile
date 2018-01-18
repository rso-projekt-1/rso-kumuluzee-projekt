FROM openjdk:8-jre-alpine

RUN mkdir /app

WORKDIR /app

ADD ./playlists-api/target/playlists-api-1.0-SNAPSHOT.jar /app

EXPOSE 8081

CMD ["java", "-jar", "playlists-api-1.0-SNAPSHOT.jar"]