FROM openjdk:8-jre-alpine

RUN mkdir /app

WORKDIR /app

ADD ./target/recommender-1.0-SNAPSHOT.jar /app

EXPOSE 8081

CMD ["java", "-jar", "recommender-1.0-SNAPSHOT.jar"]