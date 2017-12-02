FROM openjdk:8-jre-alpine
MAINTAINER Thiago Oliveira <pintowar@gmail.com>

EXPOSE 8080
VOLUME /tmp
ADD gguess.jar app.jar
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]