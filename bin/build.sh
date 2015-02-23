#! /usr/bin/env bash

IMAGE_NAME='checkbox/appli'
DIR=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)
HOME=$(cd "$DIR/.." && pwd)

docker build -t $IMAGE_NAME $HOME
