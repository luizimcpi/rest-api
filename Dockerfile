FROM adoptopenjdk/openjdk11
LABEL maintainer="luizimcpi@gmail.com"
VOLUME /tmp
ARG JAR_FILE=target/rest-api-*.jar
ADD ${JAR_FILE} rest-api.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/rest-api.jar"]