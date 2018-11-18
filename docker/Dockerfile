FROM java:8
VOLUME /tmp
COPY target/demo-1.0-SNAPSHOT.jar resource.jar
RUN bash -c "touch /resource.jar"
EXPOSE 8080
ENTRYPOINT ["java","-jar","resource.jar"]
# docker run -d -p 18080:8080 --name docker-resource leesia/resource:1.0