# mvn clean install spring-boot:repackage
# java -jar target/nombreartefacto-1.0-SNAPSHOT.jar
# 1) heroku login
# 2) heroku git:remote -a nombreappheroku
# 3) docker build -t nombreartefacto .
# 4) docker run -d -p 8080:8080 -t nombreartefacto:latest
# 5) docker ps
# 6) heroku container:login
# 7) heroku container:push web
# 8) heroku container:release web
# opcional para dyos heroku ps:scale web=1, heroku restart, heroku ps
FROM adoptopenjdk/openjdk11:alpine-jre

ARG JAR_FILE=target/cargame-1.0-SNAPSHOT.jar
WORKDIR /opt/app
COPY ${JAR_FILE} app.jar
EXPOSE $PORT
ENTRYPOINT ["java","-jar","app.jar"]