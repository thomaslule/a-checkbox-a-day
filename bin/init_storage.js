var fs = require('fs-extra');
var storage = require('../storage/storage');

storage.clearDb(function(err) {
    if (err) throw err;
    process.exit();
})
