FROM openjdk
ADD target/alti-test-0.0.1-SNAPSHOT.jar alti-test-0.0.1-SNAPSHOT.jar
EXPOSE 8090
ENTRYPOINT ["java","-Dloader.path=file:D:/poc","-jar","alti-test-0.0.1-SNAPSHOT.jar"]