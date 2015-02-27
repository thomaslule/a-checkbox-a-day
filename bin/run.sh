#! /usr/bin/env bash
# Author Thomas Bracher "thomas.bracher@cpe.fr"

IMAGE_NAME='checkbox/appli'

docker rm -f mysql_test &> /dev/null || true
docker ps | grep '0.0.0.0:8080->8080/tcp' | awk '{print $1}' | xargs docker rm -f &> /dev/null || true

docker run -d --name mysql_test -e MYSQL_ROOT_PASSWORD=toor123 -e MYSQL_USER=acad -e MYSQL_PASSWORD=secret -e MYSQL_DATABASE=acad_db mysql:5.7
docker run -d -p 8080:8080 --link mysql_test:mysql $IMAGE_NAME
