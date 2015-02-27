#! /usr/bin/env bash

# 1) build the application (using docker cache more smartly to reduce this step)
# 2) kill every container running
# 3) run the application and grep the application PID
# 4) follow (-f) the logs of the application
docker ps -aq | xargs docker rm -f > /dev/null
docker logs -f $(./bin/build.sh > /dev/null && ./bin/run.sh | tail -1)
