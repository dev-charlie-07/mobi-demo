FROM openjdk:17-jdk-slim

COPY target/demo-*.jar /demo.jar

CMD [ "java","-jar","/demo.jar" ]

