FROM openjdk:11
COPY  target/mtd-learning-mysql-0.0.1-SNAPSHOT.jar mtd-learning-mysql-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/mtd-learning-mysql-0.0.1-SNAPSHOT.jar"]