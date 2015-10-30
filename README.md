[![Build Status](https://travis-ci.org/thomaslule/a-checkbox-a-day.svg?branch=master)](https://travis-ci.org/thomaslule/a-checkbox-a-day)

# A checkbox a day

Learning javascript, angular and node.js by implementing a todolist inspired by the "bullet journal".

## Install the application

### Prerequisites

- Node.js
- A mysql database
- Java (only for tests)

### Launch the application

```` bash
cp local.json-dist local.json # put your database credentials here
npm install
npm start
````

### Test the application

Launch the selenium server:

```` bash
npm run selenium
````

Launch the tests:

```` bash
npm test
````

Only unit tests:

```` bash
npm run unit
````

Only end-to-end tests:

```` bash
npm run e2e
````
