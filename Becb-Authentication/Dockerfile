FROM openjdk:12

WORKDIR /app

expose 8082

COPY target/*.jar /app/app.jar

#COPY wait-for-it.sh /wait-for-it.sh
#COPY entrypoint.sh /entrypoint.sh
#RUN chmod +x /wait-for-it.sh
CMD ["java", "-jar", "app.jar"]