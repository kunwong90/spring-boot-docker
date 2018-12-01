FROM java:8
VOLUME /tmp
COPY spring-boot-docker-0.0.1-SNAPSHOT.jar web.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","web.jar"]
