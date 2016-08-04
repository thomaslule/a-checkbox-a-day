// Karma configuration
// Generated on Mon Oct 26 2015 23:21:56 GMT+0100 (Paris, Madrid)

module.exports = function(config) {
  config.set({

    // base path that will be used to resolve all patterns (eg. files, exclude)
    basePath: '../..',


    // frameworks to use
    // available frameworks: https://npmjs.org/browse/keyword/karma-adapter
    frameworks: ['jasmine'],


    // list of files / patterns to load in the browser
    files: [
      'http://ajax.googleapis.com/ajax/libs/angularjs/1.4.5/angular.js',
      'http://ajax.googleapis.com/ajax/libs/angularjs/1.4.5/angular-route.js',
      'http://ajax.googleapis.com/ajax/libs/angularjs/1.4.5/angular-resource.js',
      'http://ajax.googleapis.com/ajax/libs/angularjs/1.4.5/angular-mocks.js',
      'http://cdnjs.cloudflare.com/ajax/libs/moment.js/2.10.6/moment-with-locales.js',
      'http://cdnjs.cloudflare.com/ajax/libs/moment.js/2.10.6/locale/fr.js',
      'http://cdnjs.cloudflare.com/ajax/libs/angular-moment/0.10.3/angular-moment.min.js',
      'public/**/*.js',
      'test/unit/**/*Test.js'
    ],


    // list of files to exclude
    exclude: [
    ],


    // preprocess matching files before serving them to the browser
    // available preprocessors: https://npmjs.org/browse/keyword/karma-preprocessor
    preprocessors: {
    },


    // test results reporter to use
    // possible values: 'dots', 'progress'
    // available reporters: https://npmjs.org/browse/keyword/karma-reporter
    reporters: ['progress'],


    // web server port
    port: 9876,


    // enable / disable colors in the output (reporters and logs)
    colors: true,


    // level of logging
    // possible values: config.LOG_DISABLE || config.LOG_ERROR || config.LOG_WARN || config.LOG_INFO || config.LOG_DEBUG
    logLevel: config.LOG_INFO,


    // enable / disable watching file and executing tests whenever any file changes
    autoWatch: true,


    // start these browsers
    // available browser launchers: https://npmjs.org/browse/keyword/karma-launcher
    browsers: ['Firefox'],


    // Continuous Integration mode
    // if true, Karma captures browsers, runs the tests and exits
    singleRun: false,

    // Concurrency level
    // how many browser should be started simultanous
    concurrency: Infinity
  })
}
