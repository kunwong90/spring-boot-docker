FROM java:8
VOLUME /opt/logs
COPY spring-boot-docker-0.0.1-SNAPSHOT.jar web.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","web.jar"]

#docker run -d --name spring-boot-docker-prd --env ENV=prd -p 8888:8080 test/spring-boot-docker