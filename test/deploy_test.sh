#! /usr/bin/env bash

# First build project from build script
DIR=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)
HOME=$(cd "$DIR/.." && pwd)
if ! $HOME/bin/build.sh 1> /dev/null; then
	echo "Build command failed"
	exit 1
fi
echo "Application container built"

IMAGE_NAME=checkbox/appli
APPLI_IMAGE=$(docker images | grep $IMAGE_NAME)

# First we check if the appli is built
if [ -z "$APPLI_IMAGE" ]; then
	echo "No $IMAGE_NAME image found, first build the project"
	exit 1
fi

# We then test that the application is launched and can be pinged
# finally cleans everything up
# if called with no argument, considering that the script failed
function finally() {
	if [ -n "$MYSQL_PID" ]; then
		docker rm -f $MYSQL_PID 1> /dev/null
		echo "Mysql test container killed $MYSQL_PID"
	fi
	if [ -n "$APPLI_PID" ]; then
		docker rm -f $APPLI_PID 1> /dev/null
		echo "Appli container killed $APPLI_PID"
	fi
	if [ -z "$1" ]; then
		echo "test failed!"
	else
		echo $1
	fi
}
set -e
trap finally EXIT
docker rm -f mysql_test &> /dev/null || true

MYSQL_PID=$(docker run -d --name mysql_test -e MYSQL_ROOT_PASSWORD=test_toor123_password mysql:5.7)
APPLI_PID=$(docker run -d -P --link mysql_test:mysql $IMAGE_NAME)

echo "Waiting application warmup before tests"
sleep 5
APPLI_URL=$(docker port $APPLI_PID | grep 8080 | awk '{print $3}')
echo "http://$APPLI_URL/health"
curl -s -i --retry 10 --retry-delay 5 -L -f http://$APPLI_URL/health | grep "200 OK"

# TODO test mysql connection
trap - EXIT

finally "Deploy test suite ok!"
