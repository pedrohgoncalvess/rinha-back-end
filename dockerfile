#FROM sbtscala/scala-sbt:graalvm-ce-22.3.3-b1-java17_1.9.8_3.3.1
FROM sbtscala/scala-sbt:eclipse-temurin-focal-17.0.10_7_1.9.8_3.3.1

WORKDIR /app

COPY . /app

RUN sbt update

EXPOSE 8080

CMD ["sbt", "run"]