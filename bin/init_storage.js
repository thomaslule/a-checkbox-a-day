var fs = require('fs-extra');
var storage = require('../storage/storage');

function execute(script, callback) {
    fs.readFile(script, 'utf8', function(err, data) {
        if (err) {
            return callback(err);
        }
        storage.execute(data, function(err) {
            if (err) {
                return callback(err);
            }
            return callback();
        });
    });
}

execute('./storage/drop_db.sql', function(err) {
    if (err) throw err;
    execute('./storage/init_db.sql', process.exit);
});
