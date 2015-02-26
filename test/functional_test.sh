#! /usr/bin/env bash

# run acceptance tests

DIR=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)
HOME=$(cd "$DIR/acceptance" && pwd)
if ! $DIR/acceptance/build.sh 1> /dev/null; then
    echo "Build command failed"
    exit 1
fi
echo "Acceptance image built"

IMAGE_NAME='checkbox-test/acceptance'

# First we check if the appli is built
IMAGE=$(docker images | grep $IMAGE_NAME)
if [ -z "$IMAGE" ]; then
    echo "No $IMAGE_NAME image found, first build the project"
    exit 1
fi

PIDS=$($DIR/../bin/run.sh)
MYSQL_PID=$(echo "$PIDS" | head -1)
APPLI_PID=$(echo "$PIDS" | tail -1)
SELENIUM_PID=$(docker run --name selenium -d -p 4444:4444 selenium/standalone-chrome:2.44.0)
sleep 7
APPLI_URL=$(docker port $APPLI_PID | grep 8080 | awk '{print $3}')
echo "Running test with environment : $APPLI_URL"
OUTPUT=$(docker run --link selenium:selenium -e APPLI_URL="$APPLI_URL" -it $IMAGE_NAME)
docker rm -f $MYSQL_PID $SELENIUM_PID $APPLI_PID
if echo "$OUTPUT" | grep '100% passed'; then
    echo "Functional tests passed"
else
    echo "$OUTPUT"
    exit 1
fi

