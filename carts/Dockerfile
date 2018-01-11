FROM openjdk:8-jre-alpine

RUN mkdir /app

WORKDIR /app

ADD ./carts-api/target/carts-api-1.0-SNAPSHOT.jar /app

EXPOSE 8083

CMD ["java", "-jar", "carts-api-1.0-SNAPSHOT.jar"]