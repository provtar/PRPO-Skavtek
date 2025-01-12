FROM openjdk:17-alpine
RUN mkdir /app
ADD api/target/api-1.0.0-SNAPSHOT.jar /app
EXPOSE 8071
CMD java -DDB_URL=${DB_URL} -DDB_USER=${DB_USER} -DDB_PSW=${DB_PSW} -DDB_DRIVER=${DB_DRIVER} -DKAFKA_URL=${KAFKA_URL} -jar app/api-1.0.0-SNAPSHOT.jar
HEALTHCHECK NONE
