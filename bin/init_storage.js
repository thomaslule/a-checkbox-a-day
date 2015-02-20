var fs = require('fs');
var storage = require('../storage/storage');

fs.readFile('./storage/init_db.sql', 'utf8', function(err, data) {
    if (err) throw err;
    // execute script, then exit
    storage.execute(data, process.exit);
});
