FROM eclipse-temurin:17-jdk-alpine AS build
COPY --chown=docker:docker . /home/db6/src
WORKDIR /home/db6/src
RUN ./gradlew build --no-daemon

FROM eclipse-temurin:17-jre-alpine
EXPOSE 8080
RUN mkdir /app
COPY --from=build /home/db6/src/build/libs/legal-helper*SNAPSHOT.jar /app/spring-boot-application.jar
ENTRYPOINT ["java", "-jar", "/app/spring-boot-application.jar"]
