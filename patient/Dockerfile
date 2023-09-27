# Utiliser l'image OpenJDK 21 comme image de base
FROM openjdk:18-slim

# Créer un nouveau répertoire pour les fichiers de l'application
RUN mkdir /app

# Copier les fichiers de l'application depuis la machine hôte vers le système de fichiers de l'image
COPY . /app

# Définir le répertoire pour l'exécution des commandes ultérieures
WORKDIR /app

# Exposer le port 8081 (assurez-vous que cela correspond au port utilisé par l'application dans votre code)
EXPOSE 8081

# Dépendances de la base de données MySQL
RUN apt-get update && apt-get install -y default-mysql-client

# Commande d'attente pour s'assurer que la base de données est accessible avant de lancer l'application
CMD ["sh", "-c", "while ! mysqladmin ping -hdb -u$root -p$root --silent; do sleep 1; done; java -jar target/spring-boot-skeleton-0.0.1-SNAPSHOT.jar"]
