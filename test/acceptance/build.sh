#! /usr/bin/env bash

# Build the acceptance image
DIR=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)
IMAGE_NAME='checkbox-test/acceptance'

# Generate gemlock file if it does not exists
GEMFILE_LOCK=$DIR/Gemfile.lock
if [ -f "$GEMFILE_LOCK" ]; then
    echo "Gemfile.lock already present. If corrupted run :"
    echo "docker run --rm -v "$DIR":/usr/src/app -w /usr/src/app ruby:2.2 bundle install"
else
    docker run --rm -v "$DIR":/usr/src/app -w /usr/src/app ruby:2.2 bundle install
fi

docker build -t $IMAGE_NAME $DIR
