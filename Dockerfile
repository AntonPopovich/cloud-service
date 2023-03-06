FROM openjdk:17-oracle

EXPOSE 8080

COPY target/diplombackend-0.0.1-SNAPSHOT.jar cloudapp.jar

CMD ["java", "-jar", "cloudapp.jar"]