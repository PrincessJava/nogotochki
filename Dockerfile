FROM gradle:8.9-jdk17 AS build
WORKDIR /app
COPY . .
RUN gradle build --no-daemon
RUN ls /app/build/libs/

FROM openjdk:17-jdk-alpine
# Устанавливаем bash
RUN apk update && apk add bash

COPY --from=build /app/build/libs/nogotochki-1.0-SNAPSHOT.jar /nogotochki.jar
COPY ./wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh

ENTRYPOINT ["/wait-for-it.sh", "db:5432", "--", "java", "-jar", "/nogotochki.jar"]
