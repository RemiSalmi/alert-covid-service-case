FROM adoptopenjdk/openjdk11:latest
ENV DB_URL null
ENV DB_USER null
ENV DB_PASSWORD null

ARG JAR_FILE=build/libs/alert-covid-service-user-0.0.1-SNAPSHOT.jar


WORKDIR /opt/app

COPY ${JAR_FILE} app.jar
EXPOSE 8081
ENTRYPOINT java -DDB_URL=$DB_URL -DDB_USER=$DB_USER -DDB_PASSWORD=$DB_PASSWORD -jar app.jar
