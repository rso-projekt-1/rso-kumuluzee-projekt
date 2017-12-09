FROM openjdk:8-jre-alpine

RUN mkdir /app

WORKDIR /app

ADD ./orders-api/target/orders-api-1.0-SNAPSHOT.jar /app

EXPOSE 8081

CMD ["java", "-jar", "orders-api-1.0-SNAPSHOT.jar"]