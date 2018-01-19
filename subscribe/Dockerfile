FROM openjdk:8-jre-alpine

RUN mkdir /app

WORKDIR /app

ADD ./subscribe-api/target/subscribe-api-1.0-SNAPSHOT.jar /app

EXPOSE 8081

CMD ["java", "-jar", "subscribe-api-1.0-SNAPSHOT.jar"]