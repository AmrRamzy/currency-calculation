FROM openjdk:11
EXPOSE 8081
ENV EUREKA_SERVER=service-discovery
VOLUME [ "/DockerWorkspace" ]
ENTRYPOINT ["java","-jar","/DockerWorkspace/currency-calculation/target/currency-calculation-0.0.1-SNAPSHOT.jar"]