[![Build Status](https://travis-ci.org/thomaslule/a-checkbox-a-day.svg?branch=master)](https://travis-ci.org/thomaslule/a-checkbox-a-day)

# A checkbox a day

Learning javascript and node.js by implementing a todolist inspired by the "bullet journal".

## Install the application

#### Prerequisites

- Node.js
- A mysql database

#### Install

```` bash
cp local.json-dist local.json # put your database credentials here
npm install
node bin/init_storage.js
npm start
````

## Setup a development environment

### Linux environment

If docker is available on your platform, it's pretty straight forward.

```` bash
./bin/build.sh # builds the images
./bin/dev-run.sh # runs the application with mysql and shows the application output
./bin/run.sh # runs the application in the background
./test/deploy_test.sh # test the deployment of the application
./test/functional_test.sh # test the application with selenium tests (everything still in docker, you have nothing to do)
````

### Windows environment

#### Prerequisites

- Node.js
- Boot2docker
- Ruby

#### Install

```` powershell
.\bin\build_env.ps1        # setup the environment (you need to launch this only once)
.\bin\start_env.ps1        # starts the application
.\bin\stop_env.ps1         # stops the application
node .\bin\init_storage.js # empty the database
npm test                   # launch the tests
````
