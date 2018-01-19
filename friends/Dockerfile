FROM openjdk:8-jre-alpine

RUN mkdir /app

WORKDIR /app

ADD ./friends-api/target/friends-api-1.0-SNAPSHOT.jar /app

EXPOSE 8081

CMD ["java", "-jar", "friends-api-1.0-SNAPSHOT.jar"]