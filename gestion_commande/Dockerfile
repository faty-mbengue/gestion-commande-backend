# Dockerfile pour Spring Boot
FROM openjdk:21-jdk-slim

# Définir le répertoire de travail
WORKDIR /app

# Copier le JAR généré par Maven
COPY target/*.jar app.jar

# Exposer le port de l'application
EXPOSE 8080

# Commande pour exécuter l'application
ENTRYPOINT ["java", "-jar", "app.jar"]