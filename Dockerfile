FROM openjdk:17
EXPOSE 8888
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} learning.jar
ENTRYPOINT ["java","-jar","learning.jar"]