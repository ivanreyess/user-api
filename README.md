![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white) ![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white) ![GitHub](https://img.shields.io/badge/github-%23121011.svg?style=for-the-badge&logo=github&logoColor=white) ![Apache Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white) ![Apache Tomcat](https://img.shields.io/badge/apache%20tomcat-%23F8DC75.svg?style=for-the-badge&logo=apache-tomcat&logoColor=black)


# User api

Sample backend application for user api  based on *Spring boot*

## Directory Structure
```bash
├── src
├── Dockerfile
├── build.sh
├── docker-compose.yml
```

#### src :satellite:
This directory the source code.

### Dockerfile :whale:
Dockerfile to build a docker image.

### docker-compose.yml :whale:
docker-compose file to run all microservices.

### build.sh :building_construction:
Script for building the projects and run them on containers.

### Requirements :white_check_mark:

* jdk 17
* docker
* docker-compose

### Running  the application :star:

To run the application  you can manually build all the projects and run them individually or executing the scripts provided inside this project there is a build.sh file, do not forget to make them executable in order to run it.

```
chmod +x build.sh
chmod +x start.sh
chmod +x stop.sh
```

  * build.sh: Builds all the projects and then deploys them using docker-compose.
  * stop.sh: Stop all docker containers with one script.
  * start.sh: Start all containers previously built.

To execute the desired script you can copy and paste on your terminal.

```
./build.sh
./start.sh
./stop.sh
```

### Consuming APIs :star:

* Swagger doc: http://localhost:8080/swagger-ui/index.html
* User entrypoint: http://localhost:8080/api/users/

