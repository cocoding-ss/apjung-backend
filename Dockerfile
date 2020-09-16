FROM openjdk:11.0.7-jdk
EXPOSE 8080

ARG JAR_FILE=api/build/libs/api-1.0.jar
ADD ${JAR_FILE} apjung-backend.jar

ENTRYPOINT ["java", "-Dfile.encoding=UTF-8", "-jar", "-Duser.timezone=Asia/Seoul", "/apjung-backend.jar"]