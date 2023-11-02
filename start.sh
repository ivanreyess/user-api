#!/bin/bash
echo 'Starting services...'

docker container start $(docker ps -a | grep user-service:v1 | awk '{print $1}')

echo 'Services UP'
