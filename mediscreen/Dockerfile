# Use the OpenJDK 18 image as the base image
FROM openjdk:21-slim

#Create a new app directory for my application files
RUN mkdir /app
#Copy the app file from host machine to image filesystem

COPY . /app
#Set the directory for executing futur commands
WORKDIR /app

#Run the main class
CMD ["java","-jar","target/blogDataLayer-0.0.1-SNAPSHOT.jar"]

