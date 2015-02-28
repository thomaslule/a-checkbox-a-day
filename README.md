[![Build Status](https://travis-ci.org/thomaslule/a-checkbox-a-day.svg)](https://travis-ci.org/thomaslule/a-checkbox-a-day)

# A checkbox a day

Learning javascript and node.js by implementing a todolist vaguely inspired by the "bullet journal".

## Install:
    cp local.json-dist local.json # db credentials go here
    npm install
    node bin/init_storage.js
    npm -g install nodemon
    npm start

## Linux environment

If docker is available on your platform, it's pretty straight forward.

```` bash
./bin/build.sh # builds the images
./bin/dev-run.sh # runs the application with mysql and shows the application output
./bin/run.sh # runs the application in the background
./test/deploy_test.sh # test the deployment of the application
./test/functional_test.sh # test the application with selenium tests (everything still in docker, you have nothing to do)
````

## Windows environment

You can install vagrant to have a linux environment, yet to be improve:

```` powershell
vagrant up
vagrant ssh 
# if you can't run docker as vagrant user, try:
# vagrant halt
# Then up once more
````
