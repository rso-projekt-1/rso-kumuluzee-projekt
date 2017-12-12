FROM openjdk:8-jre-alpine

RUN mkdir /app

WORKDIR /app

ADD ./target/demo-1.0-SNAPSHOT.jar /app

EXPOSE 8082

CMD ["java", "-jar", "demo-1.0-SNAPSHOT.jar"]