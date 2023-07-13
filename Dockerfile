FROM alpine:3.17
RUN apk add --update openjdk8-jre && mkdir app
COPY /target/wfh-reporting-0.0.1-SNAPSHOT.jar /app/app.jar
WORKDIR app
ENTRYPOINT ["nohup java -jar app.jar &"]