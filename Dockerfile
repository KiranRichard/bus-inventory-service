#INSTALLATION OF THE OPERATING SYSTEM
FROM eclipse-temurin:17-jdk
COPY target/inventory-0.0.1-SNAPSHOT.jar inventory.jar
EXPOSE 9091
ENTRYPOINT ["java","-jar","inventory.jar"]