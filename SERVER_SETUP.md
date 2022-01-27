At first taking a VM in Microsoft Azure
sudo apt update
sudo apt upgrade

install docker and docker-compose
https://docs.docker.com/engine/install/debian/
https://docs.docker.com/compose/install/#install-compose-on-linux-systems
mkdir backend-app
cd backend-app
nano Dockerfile

FROM openjdk:12-jdk-alpine
ARG JAR_FILE=*.war
COPY ${JAR_FILE} app.war
ENTRYPOINT ["java","-jar","/app.war"]

Creating a docker network:
sudo docker network create -d bridge team-tracker-network

Creating a docker mysql container:
sudo docker run --name=mysql --network=team-tracker-network --network-alias=backend-database-network -e MYSQL_ROOT_PASSWORD=hello -d mysql

Packaging the app:
./mvnw package -Dmaven.test.skip=true

Sending app to server:
scp -i ../../team-tracker-vm_key.pem target/backend-0.0.1-SNAPSHOT.war sajjad@team-tracker.servehttp.com:/home/sajjad/backend-app

To enter into a docker container:
sudo docker exec -it 6d18b2df5890 bash

Enter into mysql:
mysql -u root -p

Create Database:
create database team_tracker_db;

Building backend-app docker image:
sudo docker build -t backend-app .
sudo docker run --name=backend-app -p 80:8080 --network=team-tracker-network --network-alias=backend-app-network -d backend-app

To see logs of a docker container:
sudo docker logs e3f3eaaf2edf

To kill a docker container:
sudo docker kill e3f3eaaf2edf

To remove a docker container:
sudo docker rm e3f3eaaf2edf

To check the running (with killed) docker containers:
sudo docker ps -a

To enter into a docker container:
sudo docker exec -it 6d18b2df5890 bash
