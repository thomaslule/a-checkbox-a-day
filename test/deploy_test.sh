#! /usr/bin/env bash

# First build project from build script
DIR=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)
HOME=$(cd "$DIR/.." && pwd)
if ! $HOME/bin/build.sh 1> /dev/null; then
    echo "Build command failed"
    exit 1
fi
echo "Application image built"

IMAGE_NAME=checkbox/appli

# First we check if the appli is built
APPLI_IMAGE=$(docker images | grep $IMAGE_NAME)
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
fi
if [ -n "$APPLI_PID" ]; then
    docker kill $APPLI_PID 1> /dev/null
fi
if [ -z "$1" ]; then
    printf ' X\n'
    echo "test failed!"
else
    echo $1
fi
}
set -e
trap finally EXIT
docker rm -f mysql_test &> /dev/null || true

APPLI_PID=$(docker run -d -P $IMAGE_NAME)
printf '***** no mysql test'
sleep 5
APPLI_URL=$(docker port $APPLI_PID | grep 8080 | awk '{print $3}')
curl -siL http://$APPLI_URL/health | grep "500 Internal Server Error" 1> /dev/null
printf ' O\n'
docker kill $APPLI_PID 1> /dev/null

PIDS=$($HOME/bin/run.sh)
MYSQL_PID=$(echo "$PIDS" | head -1)
APPLI_PID=$(echo "$PIDS" | tail -1)
printf '***** complete application test'
sleep 7
APPLI_URL=$(docker port $APPLI_PID | grep 8080 | awk '{print $3}')
OUTPUT=$(curl -siL http://$APPLI_URL/health)
echo "$OUTPUT" | grep "200 OK" 1> /dev/null
printf ' O\n'

# TODO test mysql connection
trap - EXIT

finally "Deploy test suite ok!"
