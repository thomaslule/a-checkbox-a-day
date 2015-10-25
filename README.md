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

Install the test tools:

```` bash
npm install -g protractor
webdriver-manager update
````

Launch the selenium server:

```` bash
webdriver-manager start
````

Launch the tests:

```` bash
npm test
````
