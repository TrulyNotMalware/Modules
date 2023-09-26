FROM eclipse-temurin:17.0.5_8-jdk
ARG JAR_FILE_NAME=test
ARG SERVER_PORT=80

ARG PROFILES=prod
ENV PROFILE=${PROFILES}
COPY ./build/libs/$JAR_FILE_NAME.jar /app.jar

EXPOSE $SERVER_PORT
ENTRYPOINT exec java -jar -Dspring.profiles.active=${PROFILE} -Duser.timezone=Asia/Seoul /app.jar