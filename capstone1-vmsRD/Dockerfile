FROM openjdk:11
ADD target/users-mysql.jar users-mysql.jar
EXPOSE 8089
ENTRYPOINT ["java", "-jar", "users-mysql.jar"]
