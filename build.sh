#!/bin/bash
echo $PWD
./mvnw clean install
docker-compose up -d