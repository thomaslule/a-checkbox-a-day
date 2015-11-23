exports.config = {
  framework: 'jasmine2',
  sauceUser: process.env.SAUCE_USERNAME,
  sauceKey: process.env.SAUCE_ACCESS_KEY,
  specs: ['monthTest.js'],
  capabilities: {
    browserName: 'firefox'
  }
}
