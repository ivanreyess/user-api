#!/bin/bash
echo 'Stopping services'

docker container stop $(docker ps -a | grep user-service:v1 | awk '{print $1}')

echo 'Services stopped'

