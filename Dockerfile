FROM openjdk:11
COPY  target/mtd-learning-mysql.jar mtd-learning-mysql.jar
ENTRYPOINT ["java","-jar","/mtd-learning-mysql.jar"]