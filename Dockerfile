# mvn clean install spring-boot:repackage
# java -jar target/application-1.0-SNAPSHOT.jar
# 1) heroku login
# 2) heroku git:remote -a nombreapphekoku
# 3) docker build -t web .
# 4) docker run -d -p 8080:8080 -t web:latest
# 5) docker ps
# 6) heroku container:login
# 7) heroku container:push web
# 8) heroku container:release web
FROM adoptopenjdk/openjdk11:alpine-jre

ARG JAR_FILE=target/application-1.0-SNAPSHOT.jar
WORKDIR /opt/app
COPY ${JAR_FILE} app.jar
EXPOSE $PORT
ENTRYPOINT ["java","-jar","app.jar"]